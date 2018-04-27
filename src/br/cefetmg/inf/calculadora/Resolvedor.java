package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.memoria.Variavel;

public class Resolvedor {

    protected static boolean varConteudoVariavel = false;

    protected static EstruturaMemoria variaveis = EstruturaMemoria.getInstancia();

    protected static Object operando1, operando2;
    protected static Object elementoAtual;

    protected static String operador;
    protected static String variavelAnterior;

    public static Object getOperando1() {
        return operando1;
    }

    public static void setOperando1(Object operando1) {
        Resolvedor.operando1 = operando1;
    }

    public static Object getOperando2() {
        return operando2;
    }

    public static void setOperando2(Object operando2) {
        Resolvedor.operando2 = operando2;
    }

    public static Object getElementoAtual() {
        return elementoAtual;
    }

    public static void setElementoAtual(Object elementoAtual) {
        Resolvedor.elementoAtual = elementoAtual;
    }

    public static String getOperador() {
        return operador;
    }

    public static void setOperador(String operador) {
        Resolvedor.operador = operador;
    }

    public static boolean possuiVariaveis(Pilha pBase, Pilha pAux) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        boolean resultado = false;
        //
        do {
            elementoAtual = pBase.desempilha();
            //
            if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
                if (varConteudoVariavel == false) {
                    resultado = true;
                } else {
                    throw new ExcecaoExpressaoInvalida("Expressão:\n\ta variável posssui ela mesma em seu conteúdo");
                }
            }
            pAux.empilha(elementoAtual);
        } while (pBase.pilhaVazia() == false);
        pAux.transfereConteudo(pBase);

        return resultado;
    }

    public static void resolveVariaveis(Pilha pBase, Pilha pAux) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        do {
            elementoAtual = pBase.desempilha();
            if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
                Variavel varEncontrada = variaveis.procuraVariavel(elementoAtual.toString());

                if (varEncontrada.getTipo().equals("expressao")) {
                    varConteudoVariavel = true;
                    elementoAtual = Calculadora.iniciaCalculadora((String) varEncontrada.getConteudo());
                    varConteudoVariavel = false;
                } else if (varEncontrada.getTipo().equals("int") || varEncontrada.getTipo().equals("double")) {
                    elementoAtual = varEncontrada.getConteudo();
                }
            }
            pAux.empilha(elementoAtual);
        } while (pBase.pilhaVazia() == false);
        pAux.transfereConteudo(pBase);
    }

}
