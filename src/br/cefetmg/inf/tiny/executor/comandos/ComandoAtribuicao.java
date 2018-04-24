package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.AnalisadorExpressao;
import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public final class ComandoAtribuicao extends Comando{

    public ComandoAtribuicao(Fila filaComandoAtual) throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        super(filaComandoAtual);
        
        this.analisa();
    }
    
    @Override
    public void executaComando() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        variaveis.armazenaVariavel(filaComandoAtual.removeFila().toString(), parametro);
    }
    
    @Override
    public void analisa() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        //variável para atribuição
        String nomeVar = filaComandoAtual.removeFila().toString();
        if (!AnalisadorExpressao.nomeVariavelProcede(nomeVar.toString())) {
            throw new ExcecaoExpressaoInvalida("Comando de atribuição: nome de variável na atribuição é inválido");
        }
        filaComandoAtual.insereFila(nomeVar);
        
        //valorVar
        String valorVar = filaComandoAtual.removeFila().toString();
        Calculadora.formataAnalisaExpressao(valorVar);
        parametro = valorVar;
        
        this.executaComando();
    }
}
