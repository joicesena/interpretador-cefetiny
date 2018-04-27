package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoListaVazia extends Exception {

    public ExcecaoListaVazia() {
        super("A memória está vazia! Não é possível remover variável.");
    }

    public ExcecaoListaVazia(String message) {
        super(message);
    }
}
