package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

public class TesteAnalise {
    static AnalisadorSintatico teste;
    public static void main(String[] args) {
        String strTeste = "x:=    2 x:=3 end";
        teste = new AnalisadorSintatico(strTeste);
    }
}
