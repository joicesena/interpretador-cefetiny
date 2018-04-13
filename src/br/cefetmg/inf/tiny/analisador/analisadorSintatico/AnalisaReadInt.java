package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

import br.cefetmg.inf.tiny.analisador.Analisador;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public class AnalisaReadInt extends Analisador {
    private String termo;
    private Pilha pilhaParenteses;
    
    public AnalisaReadInt () throws ExcecaoErroSintatico {
        //
        //
            System.out.println("entrou no AnalisaReadInt");
        //
        //

        pilhaParenteses = new Pilha();
        termo = "r";
        estado1();
    }
    
    private void estado1 () throws ExcecaoErroSintatico {
        // r
        int x = caracteresCodigo.getCaractereASCII();
        
        //
        //
            System.out.println("entrou no estado1 -> " + termo);
        //
        //
        
        if (x == 101) {
            // é um e
            termo += (char)x;
            estado2();
        } else if (x == 3) {

            //
            //
                System.out.println("acabou o programa antes do previsto: erro");
            //
            //

            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;

            //
            //
                System.out.println("o outro caractere é algo diferente do previsto no comando. pode ser uma variável");
            //
            //

            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }
    
    private void estado2 () throws ExcecaoErroSintatico {
        // re

        //
        //
            System.out.println("entrou no estado2 -> " + termo);
        //
        //

        int x = caracteresCodigo.getCaractereASCII();
        if (x == 97) {
            // é um a
            termo += (char)x;
            estado3();
        } else if (x == 3) {
            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }

    private void estado3 () throws ExcecaoErroSintatico {
        // rea

        //
        //
            System.out.println("entrou no estado2 -> 'rea'");
        //
        //

        int x = caracteresCodigo.getCaractereASCII();
        if (x == 100) {
            // é um d
            termo += (char)x;
            estado4();
        } else if (x == 3) {
            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }

    private void estado4 () throws ExcecaoErroSintatico {
        // read

        //
        //
            System.out.println("entrou no estado4 -> " + termo);
        //
        //

        int x = caracteresCodigo.getCaractereASCII();

        if ((char)x == 'I') {
            // é um I            
            termo += (char)x;
            estado5();
        } else if (x == 3) {
            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }

    private void estado5 () throws ExcecaoErroSintatico {
        // readI

        //
        //
            System.out.println("entrou no estado5 -> " + termo);
        //
        //

        int x = caracteresCodigo.getCaractereASCII();
        if (x == 110) {
            // é um n
            termo += (char)x;
            estado6();
        } else if (x == 3) {
            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }

    private void estado6 () throws ExcecaoErroSintatico {
        // readIn

        //
        //
            System.out.println("entrou no estado6 -> " + termo);
        //
        //

        int x = caracteresCodigo.getCaractereASCII();
        if (x == 116) {
            // é um t
            termo += (char)x;
            estado7();
        } else if (x == 3) {
            // end of text
            throw new ExcecaoErroSintatico();
        } else {
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        }
    }

    private void estado7 () throws ExcecaoErroSintatico {
        // readInt

        //
        //
            System.out.println("entrou no estado7 -> " + termo);
        //
        //

        int x = caracteresCodigo.getCaractereASCII();
        // readInt
        if (x == 32) {
            // espaço " "
            estado7();
        } else if (x == 13) {
            // quebra de linha
            // são 2 caracteres, 13 e 10
            caracteresCodigo.getCaractereASCII();
            estado7();
        } else if (x == 40) {
            // abre parênteses "("
            try {
                pilhaParenteses.empilha("(");
                
                //
                    pilhaParenteses.imprimePilha();
                //

                // o comando ta completo, insere o readInt na fila
                filaExecucao.insereFila("readInt");

                termo = "(";
                estado8();
                return;
            } catch (ExcecaoPilhaVazia erro) {
                System.err.println(erro.getMessage());
            }
        } else if (((x >= 48)&&(x <= 57)) || ((x >= 97)&&(x <= 122))) {
            // letra ou número
            termo += (char)x;
            //
            // AnalisaAtribuicao = new AnalisaAtribuicao (termo)
            //
        } else {
            throw new ExcecaoErroSintatico();
        }
    }
    
    private void estado8 () throws ExcecaoPilhaVazia, ExcecaoErroSintatico {
        int x = caracteresCodigo.getCaractereASCII();
        // readInt(

        //
        //
            System.out.println("entrou no estado8 -> " + termo);
            System.out.println((char)x);
        //
        //

        if (x == 32) {
            // espaço " "
            estado8();
        } else if (x == 13) {
            // quebra de linha
            // são 2 caracteres, 13 e 10
            caracteresCodigo.getCaractereASCII();
            estado8();
        } else if (x == 40) {
            // abre parênteses "("
            pilhaParenteses.empilha("(");

                //
                    pilhaParenteses.imprimePilha();
                //

            termo += "(";
            estado8();
        } else if (x == 41) {
            //fecha parênteses
            termo += ")";
            
            pilhaParenteses.desempilha();

                //
                    pilhaParenteses.imprimePilha();
                //


            if (!pilhaParenteses.estaVazia()) {
                estado9();
            }
        } else if (((x >= 48)&&(x <= 57)) || ((x >= 97)&&(x <= 122))) {
            // letra ou número
            termo += (char)x;
            estado8();
        } else {
            throw new ExcecaoErroSintatico();
        }
    }
    
    private void estado9 () {

        //
        //
            System.out.println("entrou no estado9 -> " + termo);
        //
        //

        filaExecucao.insereFila(termo);
    }    
    
}