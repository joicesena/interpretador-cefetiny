package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

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

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
    }
    
}
