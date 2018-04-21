package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public final class Organizador {

    private static String[] expressao;

    static Pilha organizaExpressaoNaPilha(String expressaoCompleta) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        expressao = expressaoCompleta.split("");

        int i = 0;
        int contaPonto = 0;

        Pilha pBase = new Pilha();

        String concatenador = "";
        //
        do {
            if (Constantes.INTEIROS.contains(expressao[i])) {
                while (i < expressao.length && (Constantes.INTEIROS.contains(expressao[i])
                        || expressao[i].equals("."))) {
                    if (expressao[i].equals(".")) {
                        contaPonto++;
                    }
                    concatenador += expressao[i];
                    i++;
                }
                if (contaPonto == 1 || contaPonto == 0) {
                    pBase.empilha(Double.parseDouble(concatenador));
                    concatenador = "";
                    contaPonto = 0;
                } else {
                    throw new ExcecaoExpressaoInvalida("Expressão: Valor informado é inválido");
                }
            } else if (Constantes.ALFABETO.contains(expressao[i])) {
                while (i < expressao.length
                        && ((Constantes.ALFABETO.contains(expressao[i])) || (Constantes.INTEIROS.contains(expressao[i])))) {
                    concatenador += expressao[i];
                    i++;
                }
                pBase.empilha(concatenador);
                concatenador = "";
            } else if (Constantes.OP_RELACIONAIS.contains(expressao[i]) || Constantes.OP_BIN_ARITMETICOS.contains(expressao[i])) {
                pBase.empilha(expressao[i]);
                i++;
            } else if (expressao[i].equals("\"")) {
                if (expressao[expressao.length - 1].equals("\"")) {
                    while (i < expressao.length
                            && ((Constantes.ALFABETO.contains(expressao[i])) || (Constantes.INTEIROS.contains(expressao[i])
                            || expressao[i].equals("\"")))) {
                        pBase.empilha(expressao[i]);
                        i++;
                    }
                }
            } else if (expressao[i].equals("(") || expressao[i].equals(")")) {
                pBase.empilha(expressao[i]);
                i++;
            } else {
                i++;
            }
        } while (i < expressao.length);

        return pBase;
    }
}
