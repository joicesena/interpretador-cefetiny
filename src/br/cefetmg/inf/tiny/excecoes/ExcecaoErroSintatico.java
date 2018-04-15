package br.cefetmg.inf.tiny.excecoes;

public class ExcecaoErroSintatico extends Exception {

    public ExcecaoErroSintatico() {
        super("Erro sintático!!");
    }
    
    public ExcecaoErroSintatico (String descricao) {
        super("Erro sintático: \n\t" + descricao);
    }

}
