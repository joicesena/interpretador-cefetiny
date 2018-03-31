package br.cefetmg.inf.tiny.estruturasDados.pilhaDinamica;

import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public interface MetodosPilha {
    public void empilha(Object conteudo)throws ExcecaoPilhaVazia;    
    public Object desempilha()throws ExcecaoPilhaVazia;
    
    public boolean estaVazia();
    public int tamanhoPilha();
    
    public void imprimePilha()throws ExcecaoPilhaVazia;
    public void invertePilha() throws ExcecaoPilhaVazia;
    public void esvaziaPilha();
}
