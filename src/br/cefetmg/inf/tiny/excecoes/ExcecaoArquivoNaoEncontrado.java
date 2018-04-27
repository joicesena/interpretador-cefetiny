package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoArquivoNaoEncontrado extends Exception {
    public ExcecaoArquivoNaoEncontrado() {
        super("\nErro: O arquivo não foi encontrado. Verifique se você digitou o caminho corretamente.");
    }  
    
    public ExcecaoArquivoNaoEncontrado(String message){
        super(message);
    }
}
