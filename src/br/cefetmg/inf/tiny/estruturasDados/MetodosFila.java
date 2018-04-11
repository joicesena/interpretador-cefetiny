package br.cefetmg.inf.tiny.estruturasDados;

import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;

public interface MetodosFila {
    public void insereFila(Object conteudo);
    public Object removeFila() throws ExcecaoFilaVazia;
    
    public boolean estaVazia();
    public void imprimeFila() throws ExcecaoFilaVazia;
}
