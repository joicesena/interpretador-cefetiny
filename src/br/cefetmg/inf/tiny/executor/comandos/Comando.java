package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.excecoes.ExcecaoEntradaInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;

public abstract class Comando {
    protected Fila filaComandoAtual;
    protected static EstruturaMemoria variaveis;
    protected String parametro;

    public Comando(String parametro) {
        this.parametro = parametro;
        variaveis = EstruturaMemoria.getInstancia();
    }

    public Comando(Fila filaComandoAtual) {
        this.filaComandoAtual = filaComandoAtual;
        parametro = null;
        variaveis = EstruturaMemoria.getInstancia();
    }
    
    public abstract void executaComando() throws ExcecaoFilaVazia, ExcecaoPilhaVazia, ExcecaoExpressaoInvalida, ExcecaoEntradaInvalida;
    
    public abstract void analisa() throws ExcecaoFilaVazia, ExcecaoPilhaVazia, ExcecaoExpressaoInvalida, ExcecaoEntradaInvalida;
}
