package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.util.Dicionarios;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.memoria.Variavel;
import br.cefetmg.inf.util.Conversor;

public final class ResolvedorAritmetico extends Resolvedor {

    private static final String[] OPS_ARIT1 = {"sqrt", "*", "+"};
    private static final String[] OPS_ARIT2 = {"sqrt", "/", "-"};
    private static final String[] OPS_ARIT3 = {"sqrt", "div", "+"};
    private static final String[] OPS_ARIT4 = {"sqrt", "mod", "+"};

    public static Pilha resolveExpressaoAritmetica(Pilha pBase) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        variaveis = EstruturaMemoria.getInstancia();

        int contaVars;

        Pilha pAux = new Pilha();
        //
        if (possuiVariaveis(pBase, pAux)) {
            resolveVariaveis(pBase, pAux);
        }
        
        for (int i = 0; i < 3; i++) {
            do {
                elementoAtual = pBase.desempilha();

                if (elementoAtual.equals(OPS_ARIT1[i])
                    || elementoAtual.equals(OPS_ARIT2[i])
                    || elementoAtual.equals(OPS_ARIT3[i])
                    || elementoAtual.equals(OPS_ARIT4[i])) {
                    operador = (String) elementoAtual;
                    operando2 = pBase.desempilha();
                    if (i > 0) {
                        operando1 = pAux.desempilha();
                        pAux.empilha(resolveOperacaoBinaria(Conversor.converteObjectDouble(operando1),
                                                            (String) operador,
                                                            Conversor.converteObjectDouble(operando2)));
                    } else {
                        pAux.empilha(resolveOperacaoUnaria(Conversor.converteObjectInt(operando2)));
                    }
                } else if (Dicionarios.procuraElementoNoDicionario(elementoAtual.toString(), Dicionarios.OP_LOGICOS)) {
                    pAux.empilha(elementoAtual);
                } else {
                    pAux.empilha(elementoAtual);
                }
            } while (pBase.pilhaVazia() == false);
            pAux.transfereConteudo(pBase);
        }

        return pBase;
    }
    
    public static Object resolveOperacaoBinaria(double op1, String oper, double op2) {
        boolean deveConverter = false;
        double resultado = 0.0;

        switch (oper) {
            case "*":
                resultado = op1 * op2;
                break;

            case "div":
                resultado = op1 / op2;
                deveConverter = true;
                break;

            case "mod":
                resultado = op1 % op2;
                break;

            case "+":
                resultado = op1 + op2;
                break;

            case "-":
                resultado = op1 - op2;
                break;

            case "/":
                resultado = op1 / op2;
                if (resultado - ((Double) resultado).intValue() == 0.0) {
                    deveConverter = true;
                }
                break;
        }
        if (deveConverter) {
            return ((Double) resultado).intValue();
        }
        return resultado;
    }

    public static double resolveOperacaoUnaria(int op) {
        double resultado = 0;

        for (int i = 0; i < op; i++) {
            if (i * i == op) {
                resultado = i;
            }
        }
        return resultado;
    }
}
