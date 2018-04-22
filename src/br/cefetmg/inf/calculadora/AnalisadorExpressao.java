package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.util.Dicionarios;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;

public final class AnalisadorExpressao {

    private static boolean terminaAutomato;

    private static int contaAbreParenteses;
    private static int contaFechaParenteses;
    private static int contaAspas;

    private static Object elementoAtual;

    private static EstruturaMemoria variaveis;

    private static Pilha pCopia;
    private static Pilha pBase;

    public static Pilha inicializaAutomato(Pilha pEntrada) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        terminaAutomato = false;

        contaAbreParenteses = 0;
        contaFechaParenteses = 0;
        contaAspas = 1;

        elementoAtual = null;

        variaveis = EstruturaMemoria.getInstancia();

        pCopia = new Pilha();
        pBase = pEntrada;

        estado0();

        return pCopia;
    }

    private static void recebeProximo() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        try {
            if (terminaAutomato == false) {
                elementoAtual = pBase.desempilha();
            } else {
                return;
            }
        } catch (ExcecaoPilhaVazia e) {
            if (contaAbreParenteses == contaFechaParenteses
                && (elementoAtual instanceof Double || elementoAtual instanceof Integer
                    || elementoAtual.equals("true") || elementoAtual.equals("false")
                    || elementoAtual.equals(")") || variaveis.procuraVariavel((String) elementoAtual) != null)
                || (elementoAtual.equals("\"") && contaAspas == 2)) {
                estadoFinal();
                return;
            } else {
                estadoErro();
            }
        }

        pCopia.empilha(elementoAtual);
    }

    //Estado Inicial
    private static void estado0() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (elementoAtual.equals("true") || elementoAtual.equals("false")) {
            estado3();
        } else if (elementoAtual.equals("sqrt")) {
            estado4();
        } else if (elementoAtual.equals("not")) {
            estado5();
        } else if (elementoAtual.equals("\"")) {
            estado10();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado Número
    private static void estado1() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (terminaAutomato == true) {
            return;
        } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_BIN_ARITMETICOS)) {
            estado6();
        } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_RELACIONAIS)) {
            estado7();
        } else if (elementoAtual.equals(")")) {
            estado8();
        } else {
            estadoErro();
        }
    }

    //Estado '('
    private static void estado2() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        contaAbreParenteses++;

        recebeProximo();

        if (elementoAtual.equals("(")) {
            estado2();
        } else if (elementoAtual.equals("true") || elementoAtual.equals("false")) {
            estado3();
        } else if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("sqrt")) {
            estado4();
        } else if (elementoAtual.equals("not")) {
            estado5();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado 'true' || 'false'
    private static void estado3() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (terminaAutomato == true) {
            return;
        } else if (elementoAtual.equals("and") || elementoAtual.equals("or")) {
            estado9();
        } else if (elementoAtual.equals(")")) {
            estado8();
        } else {
            estadoErro();
        }
    }

    //Estado 'sqrt'
    private static void estado4() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado 'not'
    private static void estado5() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual.equals("true") || elementoAtual.equals("false")) {
            estado3();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado operador aritmético
    private static void estado6() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado operador relacional
    private static void estado7() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado ')'
    private static void estado8() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        contaFechaParenteses++;

        recebeProximo();

        if (terminaAutomato == true) {
            return;
        } else if (elementoAtual.equals(")")) {
            estado8();
        } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_BIN_ARITMETICOS)) {
            estado6();
        } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_RELACIONAIS)) {
            estado7();
        } else if (elementoAtual.equals("and") || elementoAtual.equals("or")) {
            estado9();
        } else {
            estadoErro();
        }
    }

    //Estado operador lógico
    private static void estado9() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual.equals("(")) {
            estado2();
        } else if (elementoAtual.equals("true") || elementoAtual.equals("false")) {
            estado3();
        } else if (variaveis.procuraVariavel((String) elementoAtual) != null) {
            estado1();
        } else {
            estadoErro();
        }
    }

    //Estado "
    private static void estado10() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        do {
            recebeProximo();
            if (terminaAutomato == true) {
                return;
            } else if (elementoAtual.equals("\"")) {
                contaAspas++;
            }
        } while (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.ALFABETO)
                 || (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.INTEIROS)
                     || elementoAtual.equals("\"")));
    }

    private static void estadoFinal() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        terminaAutomato = true;
    }

    private static void estadoErro() throws ExcecaoExpressaoInvalida {
        throw new ExcecaoExpressaoInvalida("Expressão: Semântica ou sintaxe falha");
    }
}
