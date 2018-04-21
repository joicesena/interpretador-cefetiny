package br.cefetmg.inf.util.calculadora;

import java.util.ArrayList;

public final class Constantes {

    public static final ArrayList<String> ALFABETO = new ArrayList();
    public static final ArrayList<String> INTEIROS = new ArrayList();
    public static final ArrayList<String> OP_ARITMETICOS = new ArrayList();
    public static final ArrayList<String> OP_BIN_ARITMETICOS = new ArrayList();
    public static final ArrayList<String> OP_LOGICOS = new ArrayList();
    public static final ArrayList<String> OP_RELACIONAIS = new ArrayList();

    public static void inicializaContantes() {
        OP_BIN_ARITMETICOS.add("+");
        OP_BIN_ARITMETICOS.add("-");
        OP_BIN_ARITMETICOS.add("*");
        OP_BIN_ARITMETICOS.add("/");
        OP_BIN_ARITMETICOS.add("mod");
        OP_BIN_ARITMETICOS.add("div");
        //
        OP_ARITMETICOS.add("+");
        OP_ARITMETICOS.add("-");
        OP_ARITMETICOS.add("*");
        OP_ARITMETICOS.add("/");
        OP_ARITMETICOS.add("mod");
        OP_ARITMETICOS.add("div");
        OP_ARITMETICOS.add("sqrt");
        //
        OP_LOGICOS.add(">");
        OP_LOGICOS.add(">=");
        OP_LOGICOS.add("<");
        OP_LOGICOS.add("<=");
        OP_LOGICOS.add("<>");
        OP_LOGICOS.add("=");
        OP_LOGICOS.add("not");
        
        OP_LOGICOS.add("true");
        OP_LOGICOS.add("false");
        
        OP_LOGICOS.add("and");
        OP_LOGICOS.add("or");
        //
        OP_RELACIONAIS.add(">");
        OP_RELACIONAIS.add(">=");
        OP_RELACIONAIS.add("<");
        OP_RELACIONAIS.add("<=");
        OP_RELACIONAIS.add("<>");
        OP_RELACIONAIS.add("=");
        //
        INTEIROS.add("0");
        INTEIROS.add("1");
        INTEIROS.add("2");
        INTEIROS.add("3");
        INTEIROS.add("4");
        INTEIROS.add("5");
        INTEIROS.add("6");
        INTEIROS.add("7");
        INTEIROS.add("8");
        INTEIROS.add("9");
        //
        ALFABETO.add("a");
        ALFABETO.add("b");
        ALFABETO.add("c");
        ALFABETO.add("d");
        ALFABETO.add("e");
        ALFABETO.add("f");
        ALFABETO.add("g");
        ALFABETO.add("h");
        ALFABETO.add("i");
        ALFABETO.add("j");
        ALFABETO.add("k");
        ALFABETO.add("l");
        ALFABETO.add("m");
        ALFABETO.add("n");
        ALFABETO.add("o");
        ALFABETO.add("p");
        ALFABETO.add("q");
        ALFABETO.add("r");
        ALFABETO.add("s");
        ALFABETO.add("t");
        ALFABETO.add("u");
        ALFABETO.add("v");
        ALFABETO.add("w");
        ALFABETO.add("x");
        ALFABETO.add("y");
        ALFABETO.add("z");
    }
}
