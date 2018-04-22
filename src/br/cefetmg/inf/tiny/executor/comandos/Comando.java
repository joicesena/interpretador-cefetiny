package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public abstract class Comando {
    private static Fila filaExecucao;
    
    private final String parametro;

    public Comando(String parametro) {
        this.parametro = parametro;
    }
    
    public abstract void executaComando();
}
