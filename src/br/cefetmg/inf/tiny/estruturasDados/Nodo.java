package br.cefetmg.inf.tiny.estruturasDados;

public class Nodo {
    private Object conteudo;
    private Nodo proximo;
    
    public Nodo(Object conteudo, Nodo proximo) {
        this.conteudo = conteudo;
        this.proximo = proximo;
    }
    
    public Object getConteudo() {
        return conteudo;
    }
    public void setConteudo(Object conteudo) {
        this.conteudo = conteudo;
    }

    public Nodo getProximo() {
        return proximo;
    }
    public void setProximo(Nodo proximo) {
        this.proximo = proximo;
    }
}
