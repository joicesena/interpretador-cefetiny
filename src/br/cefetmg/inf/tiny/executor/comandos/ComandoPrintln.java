package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoPrintln extends Comando{

    public ComandoPrintln(String parametro) {
        super(parametro);
    }

    public ComandoPrintln(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    @Override
    public void executaComando() {
    }
    
}
