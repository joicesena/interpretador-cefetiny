package br.cefetmg.inf.util;

public final class UtilidadesNumericas {
    public static int converterObjectParaInt(Object objetoEntrada) {
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

    public static double converterObjectParaDouble(Object objetoEntrada) {
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
    
    public static boolean temCasasDecimais(double objetoEntrada) {
        objetoEntrada = objetoEntrada - ((Double)objetoEntrada).intValue();
        return objetoEntrada > 0;
    }
}
