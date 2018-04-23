package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.util.Conversor;

public final class ResolvedorLogico extends Resolvedor {

    private static final Object[] operadores_logico1 = {"=", "not", "and", "or"};
    private static final Object[] operadores_logico2 = {"<>", "not", "and", "or"};
    private static final Object[] operadores_logico3 = {">", "not", "and", "or"};
    private static final Object[] operadores_logico4 = {"<", "not", "and", "or"};
    private static final Object[] operadores_logico5 = {">=", "not", "and", "or"};
    private static final Object[] operadores_logico6 = {"<=", "not", "and", "or"};

    public static Pilha resolveExpressaoLogica(Pilha pBase) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        Pilha pAux = new Pilha();
        //
        for (int i = 0; i < 4; i++) {
            do {
                elementoAtual = pBase.desempilha();

                if (elementoAtual.equals(operadores_logico1[i]) || elementoAtual.equals(operadores_logico2[i])
                        || elementoAtual.equals(operadores_logico3[i]) || elementoAtual.equals(operadores_logico4[i])
                        || elementoAtual.equals(operadores_logico5[i]) || elementoAtual.equals(operadores_logico6[i])) {
                    operador = (String) elementoAtual;
                    operando2 = pBase.desempilha();

                    switch (i) {
                        case 0:
                            operando1 = pAux.desempilha();
                            pAux.empilha(resolveOperacaoRelacional(Conversor.converteObjectDouble(operando1),
                                    (String) operador,
                                    Conversor.converteObjectDouble(operando2)));
                            break;
                        case 1:
                            pAux.empilha(resolveOperacaoUnaria((String) operando2));
                            break;
                        case 2:
                        case 3:
                            operando1 = pAux.desempilha();
                            pAux.empilha(resolveOperacaoBooleana((String) operando1,
                                    (String) operador,
                                    (String) operando2));
                            break;
                    }
                } else {
                    pAux.empilha(elementoAtual);
                }

            } while (pBase.pilhaVazia() == false);
            do {
                pBase.empilha(pAux.desempilha());
            } while (pAux.pilhaVazia() == false);
        }
        return pBase;
    }

    public static String resolveOperacaoRelacional(double op1, String oper, double op2) {
        boolean resultado = true;
        switch (oper) {
            case "=":
                resultado = op1 == op2;
                break;

            case "<>":
                resultado = op1 != op2;
                break;

            case ">":
                resultado = op1 > op2;
                break;

            case ">=":
                resultado = op1 >= op2;
                break;

            case "<":
                resultado = op1 < op2;
                break;

            case "<=":
                resultado = op1 <= op2;
                break;
        }
        if (resultado == false) {
            return "false";
        }
        return "true";
    }

    public static String resolveOperacaoBooleana(String op1, String oper, String op2) {
        int operando1, operando2, resultado;

        if (op1.equals("true")) {
            operando1 = 1;
        } else {
            operando1 = 0;
        }
        if (op2.equals("true")) {
            operando2 = 1;
        } else {
            operando2 = 0;
        }

        if (oper.equals("and")) {
            resultado = operando1 * operando2;
        } else {
            resultado = operando1 + operando2;
        }

        if (resultado > 0) {
            return "true";
        }
        return "false";
    }

    public static String resolveOperacaoUnaria(String op) {
        if (op.equals("true")) {
            return "false";
        }
        return "true";
    }
}
