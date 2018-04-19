package br.cefetmg.inf.tiny.interfaceConsole;

import java.util.Scanner;
import br.cefetmg.inf.tiny.entradaCodigo.*;
import java.io.FileNotFoundException;
import br.cefetmg.inf.tiny.analisador.analisadorSintatico.AnalisadorSintatico;

public class InterfaceConsole {
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner entrada = new Scanner(System.in);
        String caminhoArquivoTexto;
        
        System.out.println(
            "                _____      __     _   _                  \n" +
            "               / ____|    / _|   | | (_)                 \n" +
            "              | |     ___| |_ ___| |_ _ _ __  _   _      \n" +
            "              | |    / _ \\  _/ _ \\ __| | '_ \\| | | |  \n" +
            "              | |___|  __/ ||  __/ |_| | | | | |_| |     \n" +
            "INTERPRETADOR  \\_____\\___|_| \\___|\\__|_|_| |_|\\__, |\n" +
            "                                               __/ |     \n" +
            "                                              |___/    \n\n" +
            "Digite o caminho do arquivo texto que contém seu código:   "        
        );
        
        caminhoArquivoTexto = entrada.nextLine();
        
        try {
            LeitorDeArquivo.LeitorDeArquivo(caminhoArquivoTexto); 
            
        } catch(FileNotFoundException e) {
            System.err.println("\nErro: O arquivo '" + caminhoArquivoTexto + "' não foi encontrado."
                                + " Verifique se você digitou o caminho corretamente.");
            System.exit(0);
        }
        
        LeitorDeArquivo.imprimeCodigo();        
        AnalisadorSintatico analisador = new AnalisadorSintatico(LeitorDeArquivo.getCodigo());
    }
    
}
