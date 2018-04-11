package br.cefetmg.inf.util;

import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;

public final class ResolvedorDeExpressao {

    public static Object resolveExpressao(Pilha pBase) throws ExcecaoPilhaVazia {
        Pilha pAux = new Pilha();
        Object operando1, operando2;
        String operador;
        Object elementoSaida;
        Object[] operadores = {"^", "*", "+"};
        Object[] operadores2 = {"^", "/", "-"};
        Object[] operadores3 = {"^", "d", "+"};

        for (int i = 0; i < 3; i++) {
            do {
                elementoSaida = pBase.desempilha();

                if (elementoSaida.equals(operadores[i])
                        || elementoSaida.equals(operadores2[i])
                        || elementoSaida.equals(operadores3[i])) {
                    operador = (String) elementoSaida;
                    operando1 = pAux.desempilha();
                    operando2 = pBase.desempilha();
                    pAux.empilha(resolveOperacao(UtilidadesNumericas.converterObjectParaDouble(operando1),
                            (String) operador,
                            UtilidadesNumericas.converterObjectParaDouble(operando2)));
                } else {
                    pAux.empilha(elementoSaida);
                }

            } while (pBase.estaVazia() == false);
            do {
                pBase.empilha(pAux.desempilha());
            } while (pAux.estaVazia() == false);
        }
        return pBase.desempilha();
    }

    public static Object resolveOperacao(double op1, String oper, double op2) {
        boolean deveConverter = true;
        double resultado = 0.0;
        
        switch (oper) {
            case "^":
                resultado = op1;
                if (op1 == 1 || op2 == 0) {
                    return 1;
                }
                for (int i = 1; i < op2; i++) {
                    resultado = resultado * op1;
                }
                break;

            case "*":
                resultado = op1 * op2;
                break;

            case "d":
                resultado = op1 / op2;
                break;

            case "+":
                resultado = op1 + op2;
                break;

            case "-":
                resultado = op1 - op2;
                break;

            case "/":
                resultado = op1 / op2;
                if(UtilidadesNumericas.temCasasDecimais(resultado) == true) {
                    deveConverter = false;
                }
                break;
        }
        if(deveConverter == true) {
            return ((Double)resultado).intValue();
        }
        return resultado;
    }
}
