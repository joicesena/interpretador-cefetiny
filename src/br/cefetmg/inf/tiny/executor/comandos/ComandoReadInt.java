package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoReadInt extends Comando{

    public ComandoReadInt(String parametro) {
        super(parametro);
    }

    public ComandoReadInt(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    @Override
    public void executaComando() {
    }
    
}
