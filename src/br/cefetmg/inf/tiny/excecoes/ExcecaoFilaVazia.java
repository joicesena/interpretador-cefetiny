package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoFilaVazia extends Exception {
    public ExcecaoFilaVazia() {
        super("A fila está vazia");
    }
}
