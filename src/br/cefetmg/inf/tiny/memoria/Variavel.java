package br.cefetmg.inf.tiny.memoria;

public class Variavel<Dado> {

    private String nome;
    private String tipo;
    private Dado conteudo; 

    private Variavel<Dado> proxVar;
    
    public Variavel() {
        this.proxVar = null;
        this.nome = null;
        this.tipo = null;
        this.conteudo = null;
    }

    public Variavel(String nome, Dado valor, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.conteudo = valor;
    }
    
    public void setNome (String nome) {
        this.nome = nome;
    }
    
    public String getNome () {
        return nome;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setConteudo(Dado valor) {
        this.conteudo = valor;
    }
    
    public Dado getConteudo() {
        return this.conteudo;
    }
    
    public Variavel getProxNodo () {
        return proxVar;
    }

    public void setProxNodo (Variavel proxVar) {
        this.proxVar = proxVar;
    }

}