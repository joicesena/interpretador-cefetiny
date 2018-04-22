package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public abstract class Comando {
    private Fila filaComandoAtual;
    
    private final String parametro;

    public Comando(String parametro) {
        this.parametro = parametro;
    }

    public Comando(Fila filaComandoAtual) {
        this.filaComandoAtual = filaComandoAtual;
        parametro = null;
    }
    
    
    public abstract void executaComando();
}
