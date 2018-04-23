package br.cefetmg.inf.util;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;

public final class Conversor {

    public static int converteObjectInt(Object objetoEntrada) {
        if (objetoEntrada instanceof String) {
            try {
                return Integer.parseInt(objetoEntrada.toString());
            } catch (NumberFormatException e) {
                return -1;
            }
        } else if (objetoEntrada instanceof Number) {
            return ((Number) objetoEntrada).intValue();
        }
        return -1;
    }

    public static double converteObjectDouble(Object objetoEntrada) {
        if (objetoEntrada instanceof String) {
            try {
                return Double.parseDouble(objetoEntrada.toString());
            } catch (NumberFormatException e) {
                return -1;
            }
        } else if (objetoEntrada instanceof Number) {
            return ((Number) objetoEntrada).doubleValue();
        }
        return -1;
    }

    public static Object converteStringNumero(String strNumero) throws ExcecaoExpressaoInvalida {
        int i = 0;
        int contaPonto = 0;

        String concatenador = "";
        String[] numero = strNumero.split("");

        Object numeroFinal = strNumero;
        //
        if (Dicionarios.procuraElementoNoDicionario(numero[0], Dicionarios.INTEIROS)) {
            while (i < numero.length && (Dicionarios.procuraElementoNoDicionario(numero[i], Dicionarios.INTEIROS)
                                         || numero[i].equals("."))) {
                if (numero[i].equals(".")) {
                    contaPonto++;
                }
                concatenador += numero[i];
                i++;
            }
            if (contaPonto == 1) {
                numeroFinal = Double.parseDouble(concatenador);
            } else if (contaPonto == 0) {
                numeroFinal = Integer.parseInt(concatenador);
            } else {
                throw new ExcecaoExpressaoInvalida("Expressão: Valor informado não é válido");
            }
        }
        return numeroFinal;
    }
}
