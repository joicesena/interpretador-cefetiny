package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoEntradaInvalida extends Exception {
    public ExcecaoEntradaInvalida() {
        super("Entrada:\n\ta entrada apresentada não é válida");
    }

    public ExcecaoEntradaInvalida(String message) {
        super(message);
    }
}
