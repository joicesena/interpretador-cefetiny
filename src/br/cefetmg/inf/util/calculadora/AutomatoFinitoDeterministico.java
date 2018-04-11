package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.util.ResolvedorDeExpressao;
import java.util.Scanner;

public final class AutomatoFinitoDeterministico {
    static void maquinaDeEstados() {
        Object resultadoFinal = null;
        do {
            try {
                resultadoFinal = estadoA();
            } catch (ExcecaoPilhaVazia ex) {
                System.err.println(ex.getMessage());
                System.exit(0);
            } catch (NumberFormatException ex2) {
                System.err.println("Expressão Inválida");
                System.exit(0);
            }
            System.out.println("Resultado: " + resultadoFinal);
        } while (!"-1".equals(resultadoFinal));
        System.exit(0);
    }

    private static Object estadoA() throws ExcecaoPilhaVazia {
        Pilha pOrganizada = new Pilha();
        Scanner entrada = new Scanner(System.in);
        String expressaoEntrada = entrada.nextLine();

        try {
            pOrganizada = OrganizadorDePilha.organizarExpressao(expressaoEntrada);
        } catch (ExcecaoExpressaoInvalida ex) {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        pOrganizada.invertePilha();

        return estadoB(pOrganizada);
    }

    private static Object estadoB(Pilha pBase) throws ExcecaoPilhaVazia {
        Pilha pAux1 = new Pilha();
        Pilha pAux2 = new Pilha();
        Object elementoSaida;
        int contaFecha = 0;
        int contaAbre = 0;

        do {
            elementoSaida = pBase.desempilha();

            if (elementoSaida.equals("(")) {
                estadoC(pBase, pAux2, contaFecha, contaAbre);

            } else {
                estadoD(pAux1, elementoSaida);
            }
            if (pBase.estaVazia() == true
                    && pAux2.estaVazia() == true
                    && pAux1.tamanhoPilha() > 1) {
                estadoE(pAux1);
            }
        } while (pBase.estaVazia() == false);

        return pAux1.desempilha();
    }

    private static void estadoC(Pilha pBase, Pilha pAux2, int contaFecha, int contaAbre) throws ExcecaoPilhaVazia {
        Object elementoSaida2;

        contaAbre++;

        do {
            elementoSaida2 = pBase.desempilha();

            if (elementoSaida2.equals("(")) {
                pAux2.empilha(elementoSaida2);
                contaAbre++;
            } else if (elementoSaida2.equals(")")) {
                contaFecha++;
                if (contaAbre != contaFecha) {
                    pAux2.empilha(elementoSaida2);
                }
            } else {
                pAux2.empilha(elementoSaida2);
            }
        } while (contaAbre != contaFecha);

        pAux2.invertePilha();
        pBase.empilha(estadoB(pAux2));
        pAux2.esvaziaPilha();
    }

    private static void estadoD(Pilha pAux1, Object elementoSaida) throws ExcecaoPilhaVazia {
        pAux1.empilha(elementoSaida);
    }

    private static void estadoE(Pilha pAux1) throws ExcecaoPilhaVazia {
        pAux1.invertePilha();
        Object resultadoExpressao = ResolvedorDeExpressao.resolveExpressao(pAux1);
        pAux1.esvaziaPilha();
        pAux1.empilha(resultadoExpressao);
    }
}