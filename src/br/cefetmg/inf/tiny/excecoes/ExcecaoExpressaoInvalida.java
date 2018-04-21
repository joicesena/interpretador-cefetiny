package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoExpressaoInvalida extends Exception {

    public ExcecaoExpressaoInvalida(String msg) {
        super(msg);
    }
    
    public ExcecaoExpressaoInvalida() {
        super("Expressão Inválida");
    }
}
