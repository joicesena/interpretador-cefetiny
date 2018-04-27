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
    private static Object elementoAnterior;

    private static EstruturaMemoria variaveis;

    private static Pilha pCopia;
    private static Pilha pBase;

    public static Pilha inicializaAutomato(Pilha pEntrada) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        terminaAutomato = false;

        contaAbreParenteses = 0;
        contaFechaParenteses = 0;

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
                    || elementoAtual.equals(")") || variaveis.procuraVariavel(elementoAtual.toString()) != null)
                || (tipoElemento(elementoAtual).equals("string") && contaAspas == 2)) {
                estadoFinal();
                return;
            } else {
                estadoErro("Expressão:\n\ttérmino inesperado");
            }
        }

        pCopia.empilha(elementoAtual);
    }

    //Estado Inicial
    private static void estado0() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        elementoAnterior = elementoAtual;
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
        } else if (tipoElemento(elementoAtual).equals("string")) {
            estado10();
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else {
            estadoErro("Expresão:\n\tcomeça de forma inapropriada");
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
            estadoErro("Expressão:\n\telemento inválido após um número");
        }
    }

    //Estado '('
    private static void estado2() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        contaAbreParenteses++;

        elementoAnterior = elementoAtual;

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
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else if (tipoElemento(elementoAtual).equals("string")) {
            estado10();
        } else {
            estadoErro("Expressão:\n\telemento inválido após um '('");
        }
    }

    //Estado operador booleano
    private static void estado3() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (terminaAutomato == true) {
            return;
        } else if (elementoAtual.equals("and") || elementoAtual.equals("or")) {
            estado9();
        } else if (elementoAtual.equals(")")) {
            estado8();
        } else {
            estadoErro("Expressão:\n\telemento inválido após booleano");
        }
    }

    //Estado 'sqrt'
    private static void estado4() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else {
            estadoErro("Expressão:\n\telemento inválido após 'sqrt'");
        }
    }

    //Estado 'not'
    private static void estado5() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual.equals("true") || elementoAtual.equals("false")) {
            estado3();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else {
            estadoErro("Expressão:\n\telemento inválido após 'not'");
        }
    }

    //Estado operador aritmético
    private static void estado6() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        elementoAnterior = elementoAtual;
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else if (tipoElemento(elementoAtual).equals("string") && elementoAnterior.equals("+")) {
            estado10();
        } else {
            estadoErro("Expressão:\n\telemento inválido após operador aritmético binário");
        }
    }

    //Estado operador relacional
    private static void estado7() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        elementoAnterior = elementoAtual;
        recebeProximo();

        if (elementoAtual instanceof Double || elementoAtual instanceof Integer) {
            estado1();
        } else if (elementoAtual.equals("(")) {
            estado2();
        } else if (variaveis.procuraVariavel(elementoAtual.toString()) != null) {
            estado1();
        } else if (elementoAnterior.equals("=") && tipoElemento(elementoAtual).equals("string")) {
            estado10();
        } else {
            estadoErro("Expressão:\n\telemento inválido após operador relacional");
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
            estadoErro("Expressão:\n\telemento inválido após ')'");
        }
    }

    //Estado operador lógico
    private static void estado9() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        recebeProximo();

        if (elementoAtual.equals("(")) {
            estado2();
        } else {
            estadoErro("Expressão:\n\telemento inválido após operador lógico");
        }
    }

    //Estado '"'
    private static void estado10() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        String opAnterior = "";
        
        String[] vetStr = elementoAtual.toString().split("");
        //
        for (String posicaoAtual : vetStr) {
            if (posicaoAtual.equals("\"")) {
                contaAspas++;
            }
            if (!(Dicionarios.procuraElementoNoDicionario(posicaoAtual, Dicionarios.ALFABETO)
                    || Dicionarios.procuraElementoNoDicionario(posicaoAtual, Dicionarios.INTEIROS)
                    || posicaoAtual.equals("\""))) {
                estadoErro("Expressão:\n\telemento inválido na string");
            }
        }
        recebeProximo();
        contaAspas = 0;
        if (terminaAutomato == true) {
            return;
        }
        
        if ((elementoAnterior != null && elementoAtual.equals("=") && elementoAnterior.equals("="))
            || (elementoAnterior == null && elementoAtual.equals("="))) {
            estado7();
        } else if (elementoAtual.equals("+")) {
            estado6();
        } else if (elementoAtual.equals(")")) {
            estado8();
        } else {
            estadoErro("Expressão:\n\telemento inválido após string");
        }
    }

    private static void estadoFinal() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        terminaAutomato = true;
    }

    private static void estadoErro(String mensagem) throws ExcecaoExpressaoInvalida {
        throw new ExcecaoExpressaoInvalida(mensagem);
    }

    public static String tipoElemento(Object elemento) {
        String tipoElemento = "";
        if (elemento instanceof Integer) {
            tipoElemento = "int";
        } else if (elemento instanceof Double) {
            tipoElemento = "double";
        } else if (elemento instanceof String
                   && (((String) elemento).startsWith("\"") && ((String) elemento).endsWith("\""))) {
            tipoElemento = "string";
        } else {
            //tipoElemento = "operador";
        }
        return tipoElemento;
    }

    public static String tipoExpressao(Pilha pBase) throws ExcecaoPilhaVazia {
        Pilha pAux = new Pilha();
        String operadoresContidos = "";
        //
        do {
            elementoAtual = pBase.desempilha();
            //
            if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_ARITMETICOS)
                && (operadoresContidos.equals("") || operadoresContidos.equals("l"))) {
                operadoresContidos += "a";
            } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_LOGICOS)
                       && (operadoresContidos.equals("") || operadoresContidos.equals("a"))) {
                operadoresContidos += "l";
            }

            pAux.empilha(elementoAtual);
        } while (!pBase.pilhaVazia());
        pAux.transfereConteudo(pBase);

        return operadoresContidos;
    }

    public static boolean nomeVariavelProcede(String nomeVar) {
        boolean nomeProcede = true;

        String[] caracteresNome = nomeVar.split("");
        //
        if (!Dicionarios.procuraElementoNoDicionario(caracteresNome[0], Dicionarios.ALFABETO)) {
            nomeProcede = false;
        } else {
            for (int i = 1; i < caracteresNome.length; i++) {
                if (!(Dicionarios.procuraElementoNoDicionario(caracteresNome[0], Dicionarios.INTEIROS)
                      || Dicionarios.procuraElementoNoDicionario(caracteresNome[0], Dicionarios.ALFABETO))) {
                    nomeProcede = false;
                }
            }
        }
        return nomeProcede;
    }
}
