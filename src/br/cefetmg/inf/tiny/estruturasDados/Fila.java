package br.cefetmg.inf.tiny.estruturasDados;

import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;

public class Fila implements MetodosFila {
    
    private Nodo inicio;
    private Nodo fim;
    
    @Override
    public void insereFila(Object conteudo) {        
        Nodo novo = new Nodo(conteudo, null);
        if(estaVazia()){
            inicio = novo;
            fim = inicio;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
    }
    
    @Override
    public Object removeFila() throws ExcecaoFilaVazia {
        Object antigoInicio;
        
        if (this.estaVazia() == false) {
            antigoInicio = inicio.getConteudo();
            inicio = inicio.getProximo();
            return antigoInicio;
        } else {
            throw new ExcecaoFilaVazia();
        }
    }
    
    @Override
    public boolean estaVazia() {
        return inicio == null;
    }
    
    @Override
    public void imprimeFila() throws ExcecaoFilaVazia {
        Nodo percorre = inicio;
        String saida = "";
        if (this.estaVazia() == false) {
            while (percorre != null) {
                saida += percorre.getConteudo() + " ";
                percorre = percorre.getProximo();
            }
            System.out.println(saida);
        } else {
            throw new ExcecaoFilaVazia();
        }
    }
}
