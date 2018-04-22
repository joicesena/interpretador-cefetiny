package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoIf extends Comando{

    public ComandoIf(String parametro) {
        super(parametro);
    }

    public ComandoIf(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    @Override
    public void executaComando() {
    }
    
}
