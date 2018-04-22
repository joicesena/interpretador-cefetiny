package br.cefetmg.inf.util;

public final class Dicionarios {
    
    public static final String[] ALFABETO = new String[]{"a",
                                                         "b",
                                                         "c",
                                                         "d",
                                                         "e",
                                                         "f",
                                                         "g",
                                                         "h",
                                                         "i",
                                                         "j",
                                                         "k",
                                                         "l",
                                                         "m",
                                                         "n",
                                                         "o",
                                                         "p",
                                                         "q",
                                                         "r",
                                                         "s",
                                                         "t",
                                                         "u",
                                                         "v",
                                                         "w",
                                                         "x",
                                                         "y",
                                                         "z"};

    public static final String[] INTEIROS = new String[]{"0",
                                                         "1",
                                                         "2",
                                                         "3",
                                                         "4",
                                                         "5",
                                                         "6",
                                                         "7",
                                                         "8",
                                                         "9"};

    public static final String[] OP_ARITMETICOS = new String[]{"+",
                                                               "-",
                                                               "*",
                                                               "/",
                                                               "mod",
                                                               "div",
                                                               "sqrt"};
    
    public static final String[] OP_BIN_ARITMETICOS = new String[]{"+",
                                                                   "-",
                                                                   "*",
                                                                   "/",
                                                                   "mod",
                                                                   "div"};

    public static final String[] OP_LOGICOS = new String[]{">",
                                                           ">=",
                                                           "<",
                                                           "<=",
                                                           "<>",
                                                           "=",
                                                           "not",
                                                           "true",
                                                           "false",
                                                           "and",
                                                           "or"};

    public static final String[] OP_RELACIONAIS = new String[]{">",
                                                               ">=",
                                                               "<",
                                                               "<=",
                                                               "<>",
                                                               "="};

    public static final String[] LISTA_COMANDOS = {"print",
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
                                                   "end"};

    public static boolean procuraElementoNoDicionario(String elemento, String[] CONSTANTE) {
        for (String str : CONSTANTE) {
            if (elemento.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static int qualComando(String str) {
        int i = 0;
        for (i = 0; i < LISTA_COMANDOS.length; i++) {
            if (LISTA_COMANDOS[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

}
