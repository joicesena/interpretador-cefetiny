package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.util.Dicionarios;

public class Resolvedor {
    protected static Object operando1, operando2;
    protected static Object elementoAtual;

    protected static String operador;
    
    public static Object getOperando1() {
        return operando1;
    }

    public static void setOperando1(Object operando1) {
        Resolvedor.operando1 = operando1;
    }

    public static Object getOperando2() {
        return operando2;
    }

    public static void setOperando2(Object operando2) {
        Resolvedor.operando2 = operando2;
    }

    public static Object getElementoAtual() {
        return elementoAtual;
    }

    public static void setElementoAtual(Object elementoAtual) {
        Resolvedor.elementoAtual = elementoAtual;
    }

    public static String getOperador() {
        return operador;
    }

    public static void setOperador(String operador) {
        Resolvedor.operador = operador;
    }
    
}

//25+(14+(25*4+40-(20/2+10)))
