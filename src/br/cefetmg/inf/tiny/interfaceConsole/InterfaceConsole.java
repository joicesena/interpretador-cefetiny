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
        
        System.out.print(	
            "                _____      __     _   _                  \n" +	
            "               / ____|    / _|   | | (_)                 \n" +	
            "              | |     ___| |_ ___| |_ _ _ __  _   _      \n" +	
            "              | |    / _ \\  _/ _ \\ __| | '_ \\| | | |  \n" +	
            "              | |___|  __/ ||  __/ |_| | | | | |_| |     \n" +	
            "INTERPRETADOR  \\_____\\___|_| \\___|\\__|_|_| |_|\\__, |\n" +	
            "                                               __/ |     \n" +	
            "                                              |___/    \n\n" +	
            "Digite o caminho do arquivo texto que contém seu código: "        	
        );

        caminhoArquivoTexto = entrada.nextLine();
        
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
