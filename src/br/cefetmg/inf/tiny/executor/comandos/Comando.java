package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;

public abstract class Comando {
    protected Fila filaComandoAtual;
    protected static EstruturaMemoria variaveis;
    protected final String parametro;

    public Comando(String parametro) {
        this.parametro = parametro;
        variaveis = EstruturaMemoria.getInstancia();
    }

    public Comando(Fila filaComandoAtual) {
        this.filaComandoAtual = filaComandoAtual;
        parametro = null;
        variaveis = EstruturaMemoria.getInstancia();
    }
    
    
    public abstract void executaComando();
}
