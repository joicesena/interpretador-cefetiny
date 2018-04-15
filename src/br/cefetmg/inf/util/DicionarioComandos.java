package br.cefetmg.inf.util;

public class DicionarioComandos {
    public static final String [] LISTA_COMANDOS = {"print", 
                                                    "println",
                                                    "readInt",
                                                    "if",
                                                    "then",
                                                    "else",
                                                    "endif",
                                                    "while",
                                                    "do",
                                                    "endwhile",
                                                    "for",
                                                    "to",
                                                    "downto",
                                                    "endfor",
                                                    "end"
    };
    
    public static boolean verificaSeComando (String strComparar) {
        for (String str : LISTA_COMANDOS) {
            if (strComparar.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    public static int qualComando (String str) {
        int i = 0;
        for (i = 0; i <= LISTA_COMANDOS.length; i++) {
            if (LISTA_COMANDOS[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }
    
    
}
