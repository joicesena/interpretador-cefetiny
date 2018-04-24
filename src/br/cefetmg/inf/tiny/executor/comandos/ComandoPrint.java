package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public final class ComandoPrint extends Comando{

    public ComandoPrint(String parametro) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        super(parametro);
        
        this.analisa();
    }

    @Override
    public void executaComando() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        System.out.print(Calculadora.iniciaCalculadora(parametro));
    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        parametro = parametro.substring(1, (parametro.length() - 1));
        Calculadora.formataAnalisaExpressao(parametro);
        
        executaComando();
    }
    
}
