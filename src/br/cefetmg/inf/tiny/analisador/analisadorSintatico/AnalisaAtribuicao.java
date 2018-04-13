package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

import br.cefetmg.inf.tiny.analisador.Analisador;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public class AnalisaAtribuicao extends Analisador {
    
    public static String valorVariavel;
    public static String nomeVariavel;
    
    Object temp;
    
    public AnalisaAtribuicao (String termo) {
        if (pilhaComandos.estaVazia()) {
            nomeVariavel = termo;
            estadoInicial1();
        }
        
        try {
            temp = pilhaComandos.desempilha();
            if (temp.equals("atribuicao")) {
                // já iniciou a atribuição, portanto agora o valorVariavel deve ser modificado
                estadoInicial2();
            } else {
                // nao há atribuição iniciada, devolve o elemento à pilha
                pilhaComandos.empilha(temp);
                estadoInicial1();
            }
        } catch (ExcecaoPilhaVazia erro) {
            System.err.println(erro.getMessage());
        }
    
    }
    
    private void estadoInicial1 () {
        // estado em que analisa a operação de atribuição desde o início (nome da var)
        int x = caracteresCodigo.getCaractereASCII();
        if (((x >= 48)&&(x <= 57)) || ((x >= 97)&&(x <= 122))) {
            nomeVariavel += (char)x;
        } else if (x == 32) {
            // espaço
            estado1_2();
        } else if (x == 13) {
            // quebra de linha
            caracteresCodigo.getCaractereASCII();
            estado1_2();
        } else if (x == 58) {
            // :
            estado1_3();
        }
    }
    
    private void estado1_2 () {}

    private void estado1_3 () {}

    private void estadoInicial2 () {
    }
}
