package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoArquivoVazio extends Exception {
    public ExcecaoArquivoVazio() {
        super("\nErro: O arquivo está vazio ou possui caracteres não suportados pelo padrão ISO-8859-1.");
    }   
}
