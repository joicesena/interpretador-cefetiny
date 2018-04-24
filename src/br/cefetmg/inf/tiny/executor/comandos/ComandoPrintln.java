package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public final class ComandoPrintln extends Comando{
    int tipoImpressao;

    public ComandoPrintln(String parametro) throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        super(parametro);
        tipoImpressao = 2;
        
        this.analisa();
    }

    @Override
    public void executaComando() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        if (tipoImpressao == 2) {
            System.out.println(Calculadora.iniciaCalculadora(parametro));
        } else if (tipoImpressao == 1) {
            System.out.println(parametro);
        }
    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        parametro = parametro.substring(1, (parametro.length() - 1));
        Calculadora.formataAnalisaExpressao(parametro);
        
        String[] vetParam = parametro.split("");
        if (vetParam[0].equals("\"") && vetParam[vetParam.length - 1].equals("\"")) {
            tipoImpressao = 1;
        } else if (vetParam.length == 0) {
            tipoImpressao = 0;
        }
        
        executaComando();
    }
    
}
