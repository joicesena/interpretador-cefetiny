package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;

public final class VerificadorDeIncoerencias {

    public static boolean verificaSequenciaInvalida(Object elemento, Object elementoAnterior) throws ExcecaoExpressaoInvalida {
        //Lançar excessão se: 

        //((No início da expressão) || (Depois de Abre Parênteses) && (houver algo diferente de Operando ou Abre Parênteses))
        if ((elementoAnterior == null || elementoAnterior.equals("("))) {
            if (!elemento.equals("0") && !elemento.equals("1")
                    && !elemento.equals("2") && !elemento.equals("3")
                    && !elemento.equals("4") && !elemento.equals("5")
                    && !elemento.equals("6") && !elemento.equals("7")
                    && !elemento.equals("8") && !elemento.equals("9")
                    && !elemento.equals("(")) {
                throw new ExcecaoExpressaoInvalida();
                //(Antes de Abre Parênteses) && (houver algo diferente de Operador ou Abre Parênteses)
            }
        } else if (elemento.equals("(") && (!elementoAnterior.equals("+") && !elementoAnterior.equals("-")
                && !elementoAnterior.equals("*") && !elementoAnterior.equals("/")
                && !elementoAnterior.equals("^") && !elementoAnterior.equals("d")
                && !elementoAnterior.equals("("))) {
            throw new ExcecaoExpressaoInvalida();
            //(Antes de Fecha Parênteses) && (houver algo diferente de Operando)
        } else if (elemento.equals(")") && (elementoAnterior.equals("+") || elementoAnterior.equals("-")
                || elementoAnterior.equals("*") || elementoAnterior.equals("/")
                || elementoAnterior.equals("^") || elementoAnterior.equals("d"))) {
            throw new ExcecaoExpressaoInvalida();
            //(Depois de Fecha Parênteses) && (houver algo diferente de Operador ou Fecha Parênteses)
        } else if (elementoAnterior.equals(")") && (!elemento.equals("+") && !elemento.equals("-")
                && !elemento.equals("*") && !elemento.equals("/")
                && !elemento.equals("^") && !elemento.equals("d")
                && !elemento.equals(")"))) {
            throw new ExcecaoExpressaoInvalida();
            //(Antes de Operador) && (houver operador)
        } else if ((elemento.equals("+") || elemento.equals("-")
                || elemento.equals("*") || elemento.equals("/")
                || elemento.equals("^") || elemento.equals("d"))
                && (elementoAnterior.equals("+") || elementoAnterior.equals("-")
                || elementoAnterior.equals("*") || elementoAnterior.equals("/")
                || elementoAnterior.equals("^") || elementoAnterior.equals("d"))) {
            throw new ExcecaoExpressaoInvalida();
        }

        return true;
    }
}
