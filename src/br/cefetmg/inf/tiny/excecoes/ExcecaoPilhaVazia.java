package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoPilhaVazia extends Exception {
    public ExcecaoPilhaVazia() {
        super("A pilha está vazia");
    }
}
