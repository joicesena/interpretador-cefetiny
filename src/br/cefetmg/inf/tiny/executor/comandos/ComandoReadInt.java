package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

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

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
    }
    
}
