package br.cefetmg.inf.tiny.memoria;

public class VariavelArmazenada<T> {

    private String nomeVar;
    private T valorArmazenado; 

    private VariavelArmazenada<T> proxVariavelArmazenada;
    
    public VariavelArmazenada() {
        this.proxVariavelArmazenada = null;
        this.nomeVar = null;
        this.valorArmazenado = null;
    }

    public VariavelArmazenada(String nomeVar, T valorArmazenado) {
        this.nomeVar = nomeVar;
        this.valorArmazenado = valorArmazenado;
    }
    
    public void setDado(T valorArmazenado) {
        this.valorArmazenado = valorArmazenado;
    }
    
    public T getDado() {
        return this.valorArmazenado;
    }
    
    public void setNomeVar (String nomeVar) {
        this.nomeVar = nomeVar;
    }
    
    public String getNomeVar () {
        return nomeVar;
    }
    
    public VariavelArmazenada getProxNodo () {
        return proxVariavelArmazenada;
    }

    public void setProxNodo (VariavelArmazenada proxVariavelArmazenada) {
        this.proxVariavelArmazenada = proxVariavelArmazenada;
    }

}
