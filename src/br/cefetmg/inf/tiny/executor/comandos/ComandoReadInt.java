package br.cefetmg.inf.tiny.executor.comandos;

import java.util.Scanner;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.excecoes.ExcecaoEntradaInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import java.util.InputMismatchException;

public final class ComandoReadInt extends Comando{
    Scanner entrada = new Scanner(System.in);
    int valorRecebido;
    String nomeVariavel = null;

    public ComandoReadInt(String parametro) throws ExcecaoExpressaoInvalida, ExcecaoEntradaInvalida {
        super(parametro); 
        this.analisa();
        this.executaComando();
    }

    @Override
    public void executaComando() throws ExcecaoExpressaoInvalida {
        nomeVariavel = parametro.substring(1, parametro.length()-1); 
        variaveis = EstruturaMemoria.getInstancia();
        variaveis.armazenaVariavel(nomeVariavel, valorRecebido);
    }

    @Override
    public void analisa() throws ExcecaoEntradaInvalida, ExcecaoExpressaoInvalida {
        try {
            valorRecebido = entrada.nextInt();   
        } catch(InputMismatchException e) {
            throw new ExcecaoEntradaInvalida("Comando 'readInt':\n\to parâmetro passado não é inteiro");
        } 
    }
}
