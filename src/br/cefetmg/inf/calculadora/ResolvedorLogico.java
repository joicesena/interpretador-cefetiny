package br.cefetmg.inf.calculadora;

import static br.cefetmg.inf.calculadora.ResolvedorAritmetico.possuiVariaveis;
import static br.cefetmg.inf.calculadora.ResolvedorAritmetico.resolveVariaveis;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.util.Conversor;

public final class ResolvedorLogico extends Resolvedor {

    private static final Object[] OPS_LOGICOS1 = {"=", "not", "and", "or"};
    private static final Object[] OPS_LOGICOS2 = {"<>", "not", "and", "or"};
    private static final Object[] OPS_LOGICOS3 = {">", "not", "and", "or"};
    private static final Object[] OPS_LOGICOS4 = {"<", "not", "and", "or"};
    private static final Object[] OPS_LOGICOS5 = {">=", "not", "and", "or"};
    private static final Object[] OPS_LOGICOS6 = {"<=", "not", "and", "or"};

    public static Pilha resolveExpressaoLogica(Pilha pBase) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        Pilha pAux = new Pilha();
        //
        if (possuiVariaveis(pBase, pAux)) {
            resolveVariaveis(pBase, pAux);
        }

        for (int i = 0; i < 4; i++) {
            do {
                elementoAtual = pBase.desempilha();

                if (elementoAtual.equals(OPS_LOGICOS1[i]) || elementoAtual.equals(OPS_LOGICOS2[i])
                    || elementoAtual.equals(OPS_LOGICOS3[i]) || elementoAtual.equals(OPS_LOGICOS4[i])
                    || elementoAtual.equals(OPS_LOGICOS5[i]) || elementoAtual.equals(OPS_LOGICOS6[i])) {
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

    public static String resolveOperacaoRelacional(Object op1, String oper, Object op2) {
        boolean resultado = true;

        switch (oper) {
            case "=":
                if ((op1.toString().startsWith("\"") && op1.toString().endsWith("\""))
                    && op2.toString().startsWith("\"") && op2.toString().endsWith("\"")) {
                    resultado = op1.toString().equals(op2.toString());
                } else {
                    resultado = Conversor.converteObjectDouble(op1) == Conversor.converteObjectDouble(op2);
                }
                break;

            case "<>":
                resultado = Conversor.converteObjectDouble(op1) != Conversor.converteObjectDouble(op2);
                break;

            case ">":
                resultado = Conversor.converteObjectDouble(op1) > Conversor.converteObjectDouble(op2);
                break;

            case ">=":
                resultado = Conversor.converteObjectDouble(op1) >= Conversor.converteObjectDouble(op2);
                break;

            case "<":
                resultado = Conversor.converteObjectDouble(op1) < Conversor.converteObjectDouble(op2);
                break;

            case "<=":
                resultado = Conversor.converteObjectDouble(op1) <= Conversor.converteObjectDouble(op2);
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
