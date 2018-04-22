package br.cefetmg.inf.tiny.executor;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public final class Executor {
    private static Fila filaExecucao;
    
    public static void executaPrograma(Fila filaAnalisada) {
        filaExecucao = filaAnalisada;
    }
}
