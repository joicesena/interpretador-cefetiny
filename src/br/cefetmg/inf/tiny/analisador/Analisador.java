package br.cefetmg.inf.tiny.analisador;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public abstract class Analisador {
    protected static Fila filaExecucao;
    protected static Pilha pilhaComandos;
        
    public Fila getFilaExecucao() {
        return filaExecucao;
    }

    public void setFilaExecucao(Fila filaExecucao) {
        Analisador.filaExecucao = filaExecucao;
    }

    public Pilha getPilhaComandos() {
        return pilhaComandos;
    }

    public void setPilhaComandos(Pilha pilhaComandos) {
        Analisador.pilhaComandos = pilhaComandos;
    }
    
    public abstract void analisa() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia;
    
    protected abstract void analisaPrint() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaReadInt() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaIf() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaElse() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaEndif() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaWhile() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaEndwhile() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaFor() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaEndfor() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaAtribuicao() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
    
    protected abstract void analisaEnd() throws ExcecaoExpressaoInvalida, ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia;
}
