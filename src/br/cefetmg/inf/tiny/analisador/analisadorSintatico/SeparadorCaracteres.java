package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

public class SeparadorCaracteres {
    private String codigoCompleto;
    private char [] caractereCodigo;
    private static int contador;

    public SeparadorCaracteres (String codigoCompleto) {
        this.codigoCompleto = codigoCompleto;
        caractereCodigo = codigoCompleto.toCharArray();
        contador = 0;
    }
    
    public char getCaractere () {
        char caractere;
        
        if ((contador+1) <= caractereCodigo.length) {
            caractere = caractereCodigo[contador];
            contador++;
        } else {
            // o código terminou
            caractere = ((char) Character.getNumericValue(3));
        }

        return caractere;
    }
    
    public int getCaractereASCII () {

        int caractere;
        
        if ((contador+1) <= caractereCodigo.length) {
            caractere = (int) (caractereCodigo[contador]);
            contador++;
        } else {
            // o código terminou
            caractere = 3;
        }

        return caractere;
    }
    
}
