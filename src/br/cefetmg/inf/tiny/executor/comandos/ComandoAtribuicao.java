package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;

public class ComandoAtribuicao extends Comando{

    public ComandoAtribuicao(String parametro) {
        super(parametro);
    }

    public ComandoAtribuicao(Fila filaComandoAtual) {
        super(filaComandoAtual);
    }

    
    @Override
    public void executaComando() {
    }
    
}
