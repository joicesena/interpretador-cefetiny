package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoFor extends Comando{

    public ComandoFor(String parametro) {
        super(parametro);
    }

    public ComandoFor(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }
    
    

    @Override
    public void executaComando() {
    }
    
}
