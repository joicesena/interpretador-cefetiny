package br.cefetmg.inf.tiny.interfaceConsole;

import java.util.Scanner;
import java.io.FileNotFoundException;
import br.cefetmg.inf.tiny.analisador.AnalisadorSintatico;
import br.cefetmg.inf.tiny.entradaCodigo.LeitorArquivo;
import br.cefetmg.inf.tiny.excecoes.ExcecaoEntradaInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceConsole {
    
    public static void main(String[] args) throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        Scanner entrada = new Scanner(System.in);
        String caminhoArquivoTexto = null;
        
        
        
        

        //caminhoArquivoTexto = entrada.nextLine();
        
        
        try {
            LeitorArquivo.leArquivo(caminhoArquivoTexto); 
        } catch(FileNotFoundException e) {
           System.err.println("\nErro: O arquivo '" + caminhoArquivoTexto + "' não foi encontrado."
                                + " Verifique se você digitou o caminho corretamente.");
            System.exit(0);
        }
        
        LeitorArquivo.imprimeCodigo();        
        try {
            AnalisadorSintatico analisador = AnalisadorSintatico.getInstancia(LeitorArquivo.getCodigo());
        } catch (ExcecaoErroSintatico | ExcecaoFilaVazia | ExcecaoExpressaoInvalida | ExcecaoPilhaVazia | ExcecaoEntradaInvalida ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
