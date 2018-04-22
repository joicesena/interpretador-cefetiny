package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoPrint extends Comando{

    public ComandoPrint(String parametro) {
        super(parametro);
    }

    public ComandoPrint(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    @Override
    public void executaComando() {
    }
    
}
