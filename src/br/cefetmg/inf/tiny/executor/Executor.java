package br.cefetmg.inf.tiny.executor;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

/**
 * tirar da fila de execução principal e adicionar numa fila temporária do comando
 * passar essa fila temporária como parâmetro pro comando
 */
 
public final class Executor {
    private static Fila filaExecucao;
    
    public static void executaPrograma(Fila filaAnalisada) {
        filaExecucao = filaAnalisada;
    }
}
