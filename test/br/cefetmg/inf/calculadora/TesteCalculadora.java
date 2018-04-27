package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
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
                                                                             {"a+b", 259.0},
                                                                             {"a", 159.0},
                                                                             {"(10) mod (3)", 1.0}, 
                                                                             {"sqrt(49)", 7.0}, 
                                                                             {"(90) div (3)", 30},
                                                                             {"(true) or (false)", "true"},
                                                                             {"(true) and (false)", "false"},
                                                                             {"not((true) and (false))", "true"},
                                                                             {"2>1", "true"}, 
                                                                             {"2<2", "false"}, 
                                                                             {"2>=2", "true"}, 
                                                                             {"2>=3", "false"}, 
                                                                             {"(2=3) or (2=2)", "true"}, 
                                                                             {"(2=3) and (2=2)", "false"}, 
                                                                             {"10 <> 9", "true"}, 
                                                                             {"10 <> 10", "false"}, 
                                                                             {"10<20", "true"}, 
                                                                             {"10<=10", "true"}, 
                                                                             {"100<=99", "false"},
                                                                             {"c<2", "false"},
                                                                             {"\"a\"+\"b\"", "\"ab\""},
                                                                             {"\"a\"=\"a\"", "true"}, 
                                                                             {"d=\"a\"", "true"}};

    private static final String[] TESTE_DE_EXCECOES = new String[]{"2++1", 
                                                                   "(-1+2)", 
                                                                   "*1+2", 
                                                                   "mod(1)", 
                                                                   "(2)sqrt(3)", 
                                                                   "2 X 3",
                                                                   "true && false",
                                                                   "!3>2",
                                                                   "4..5+2",
                                                                   "(3+9)+4+"};

    public TesteCalculadora() {
    }

    @BeforeClass
    public static void BeforeClass() throws ExcecaoExpressaoInvalida {
        variaveis = EstruturaMemoria.getInstancia();
        variaveis.armazenaVariavel("a", "25+(14+(25*4+40-(20/2+10)))");
        variaveis.armazenaVariavel("b", "(((8*4+3)/7+(3+15/5)*3)*2-(19-7)/6)*2+12");
        variaveis.armazenaVariavel("c", "2");
        variaveis.armazenaVariavel("d", "\"a\"");
        //
        System.out.println("########################CALCULADORA########################");
        if (!variaveis.listaVazia()) {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Variáveis Declaradas:");

            Variavel var = variaveis.getPrimeiraVar();

            System.out.println("Variável " + var.getNome() + " = " + var.getConteudo());

            while (var.getProxNodo() != null) {
                var = var.getProxNodo();
                System.out.println("Variável " + var.getNome() + " = " + var.getConteudo());
            }
            System.out.println("-----------------------------------------------------------");
        }
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
    public void testeIniciaCalculadora() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        int j = 1;
        //
        System.out.println("#####TESTE INICIA CALCULADORA");
        //

        for (int i = 0; i < EXPRESSOES_E_RESULTADOS.length; i++, j++) {
            String expressaoBase = (String) EXPRESSOES_E_RESULTADOS[i][0];
            Object expResult = EXPRESSOES_E_RESULTADOS[i][1];
            Object result = Calculadora.iniciaCalculadora(expressaoBase);

            assertEquals(expResult, result);

            System.out.println("-> Caso de teste n°" + j + ": \n"
                               + "Expressao:\n\t"
                               + EXPRESSOES_E_RESULTADOS[i][0] + "\n"
                               + "Obteve resultado esperado:\n\t" + EXPRESSOES_E_RESULTADOS[i][1] + "\n"
                               + "----------------------------");
        }
        for (int i = 0; i < TESTE_DE_EXCECOES.length; i++, j++) {
            String expressaoBase = (String) TESTE_DE_EXCECOES[i];
            Object expResult = TESTE_DE_EXCECOES[i];

            try {
                Object result = Calculadora.iniciaCalculadora(expressaoBase);
            } catch (ExcecaoExpressaoInvalida e) {
                assert true;
                System.out.println("-> Caso de teste n°" + j + ": \n"
                                   + "Expressão:\n\t"
                                   + TESTE_DE_EXCECOES[i] + "\n"
                                   + "Obteve resultado esperado:\n\t" + e.getMessage() + "\n"
                                   + "----------------------------");
            }

        }
    }

    @Ignore
    public void testeFormataAnalisaExpressao() throws Exception {
        System.out.println("##Formata e Analisa Expressão##\n");
        String expressaoBase = "";
        Pilha expResult = null;
        Pilha result = Calculadora.formataAnalisaExpressao(expressaoBase);
        assertEquals(expResult, result);
    }
}
