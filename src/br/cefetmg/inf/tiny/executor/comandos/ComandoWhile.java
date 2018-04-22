package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoWhile extends Comando{

    public ComandoWhile(String parametro) {
        super(parametro);
    }

    public ComandoWhile(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    @Override
    public void executaComando() {
    }
    
}
