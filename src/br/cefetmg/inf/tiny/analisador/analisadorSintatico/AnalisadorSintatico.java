package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

import br.cefetmg.inf.tiny.analisador.Analisador;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;

public class AnalisadorSintatico extends Analisador {
    
    public AnalisadorSintatico(String codigo) {
        setFilaExecucao(new Fila());
        setPilhaComandos(new Pilha());
        
        setCaracteresCodigo(new SeparadorCaracteres(codigo));
        
        try {
            estadoInicial();
        } catch (ExcecaoErroSintatico erro) {
            // enviar para a interface
            System.err.println(erro.getMessage());
        }
    }
    
    private void estadoInicial () throws ExcecaoErroSintatico {
        String termo = "";
        
        int x = caracteresCodigo.getCaractereASCII();

        if (x == 101) {         // letra e
            //
            //AnalisaE automato = new AnalisaE();
            //
        } else if (x == 102) {  // letra f
            //
            //AnalisaFor automato = new AnalisaFor();
            //
        } else if (x == 105) {  // letra i
            //
            //AnalisaIf automato = new AnalisaIf();
            //
        } else if (x == 112) {  // letra p
            //
            //AnalisaPrint automato = new AnalisaPrint();
            //
        } else if (x == 114) {  // letra r
            //
            AnalisaReadInt automato = new AnalisaReadInt();
            //
        } else if (x == 116) {  // letra t
            //
            //AnalisaThen automato = new AnalisaThen();
            //
        } else if (x == 119) {  // letra w
            //
            //AnalisaWhile automato = new AnalisaWhile();
            //
        } else if ((x >= 97) && (x <= 122)) {
            // é uma letra
            termo += caracteresCodigo.getCaractere();
        } else if ((x >= 48) && (x <= 57)) {
            // é um dígito
        } else {
            throw new ExcecaoErroSintatico();
        }
    }
    
}
