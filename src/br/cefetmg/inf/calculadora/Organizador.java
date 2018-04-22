package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.util.Dicionarios;
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
            if (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.INTEIROS)) {
                while (i < expressao.length && (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.INTEIROS)
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
                    throw new ExcecaoExpressaoInvalida("Expressão: Valor informado não é válido");
                }
            } else if (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.ALFABETO)) {
                while (i < expressao.length
                        && ((Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.ALFABETO) 
                             || (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.INTEIROS))))) {
                    concatenador += expressao[i];
                    i++;
                }
                pBase.empilha(concatenador);
                concatenador = "";
            } else if (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.OP_RELACIONAIS) 
                       || Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.OP_BIN_ARITMETICOS)) {
                while (i < expressao.length
                        && ((Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.OP_RELACIONAIS)
                             || Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.OP_BIN_ARITMETICOS)))) {
                    concatenador += expressao[i];
                    i++;
                }
                pBase.empilha(concatenador);
                concatenador = "";
            } else if (expressao[i].equals("\"")) {
                if (expressao[expressao.length - 1].equals("\"")) {
                    while (i < expressao.length
                            && ((Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.ALFABETO)) 
                                || (Dicionarios.procuraElementoNoDicionario(expressao[i], Dicionarios.INTEIROS)
                            || expressao[i].equals("\"")))) {
                        pBase.empilha(expressao[i]);
                        i++;
                    }
                }
            } else if (expressao[i].equals("(") || expressao[i].equals(")")) {
                pBase.empilha(expressao[i]);
                i++;
            } else if (expressao[i].equals(" ")){
                i++;
            } else {
                throw new ExcecaoExpressaoInvalida("Expressão: Caractere '" + expressao[i] + "' não está presente no alfabeto");
            }
        } while (i < expressao.length);

        return pBase;
    }
}
