/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.memoria.Variavel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TesteCalculadora {

    private static EstruturaMemoria variaveis;

    private static final Object[][] EXPRESSOES_E_RESULTADOS = new Object[][]{{"25+(14+(25*4+40-(20/2+10)))", 159.0},
                                                                             {"(((8*4+3)/7+(3+15/5)*3)*2-(19-7)/6)*2+12", 100.0},
                                                                             {"10*(30/(6+4)+15)", 180.0},
                                                                             {"a+b", 259.0},};

    public TesteCalculadora() {
    }

    @BeforeClass
    public static void preparaClasseTeste() throws ExcecaoExpressaoInvalida {
        variaveis = EstruturaMemoria.getInstancia();
        variaveis.armazenaVariavel("a", "25+(14+(25*4+40-(20/2+10)))");
        variaveis.armazenaVariavel("b", "(((8*4+3)/7+(3+15/5)*3)*2-(19-7)/6)*2+12");
    }

    @Ignore
    @AfterClass
    public static void tearDownClass() {
    }

    @Ignore
    @Before
    public void setUp() {
    }

    @Ignore
    @After
    public void tearDown() {
    }

    @Test
    public void testIniciaCalculadora() throws Exception {
        System.out.println("#####CALCULADORA#####");

        if (!variaveis.listaVazia()) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Variáveis Declaradas:");
            
            Variavel var = variaveis.getPrimeiraVar();
            
            System.out.println("Variável " + var.getNome() + " = " + var.getConteudo());
            
            while (var.getProxNodo() != null) {
                var = var.getProxNodo();
                System.out.println("Variável " + var.getNome() + " = " + var.getConteudo());
            }
            System.out.println("--------------------------------------------------------------");
        }

        for (int i = 0; i < EXPRESSOES_E_RESULTADOS.length; i++) {
            String expressaoBase = (String) EXPRESSOES_E_RESULTADOS[i][0];
            Object expResult = EXPRESSOES_E_RESULTADOS[i][1];
            Object result = Calculadora.iniciaCalculadora(expressaoBase);
            assertEquals(expResult, result);
            System.out.println("Caso de teste n°" + (i + 1) + ": \n"
                               + EXPRESSOES_E_RESULTADOS[i][0] + "\n"
                               + "Obteve resultado esperado: " + EXPRESSOES_E_RESULTADOS[i][1] + "\n"
                               + "--------------------------------------------------------------");
        }
    }

    @Ignore
    public void testFormataAnalisaExpressao() throws Exception {
        System.out.println("##Formata e Analisa Expressão##\n");
        String expressaoBase = "";
        Pilha expResult = null;
        Pilha result = Calculadora.formataAnalisaExpressao(expressaoBase);
        assertEquals(expResult, result);
    }
}
