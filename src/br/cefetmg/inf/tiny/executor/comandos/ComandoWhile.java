package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.AnalisadorExpressao;
import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public final class ComandoWhile extends Comando{
    
    Fila filaComandosWhile;
    String expressaoWhile;

    public ComandoWhile(String parametro) {
        super(parametro);
    }

    public ComandoWhile(Fila filaComandoAtual) throws ExcecaoFilaVazia, ExcecaoPilhaVazia, ExcecaoPilhaVazia {
        super(filaComandoAtual);
        
        filaComandosWhile = new Fila();
        expressaoWhile = "";
        
        try {
            analisa();
        } catch (ExcecaoFilaVazia | ExcecaoExpressaoInvalida | ExcecaoPilhaVazia ex) {
            System.err.println(ex.getMessage());
        }

        executaComando();
    }

    @Override
    public void executaComando() throws ExcecaoFilaVazia, ExcecaoPilhaVazia{
        Pilha pilhaWhile = new Pilha();
        Pilha pBase;
        pilhaWhile.empilha("while");

        boolean acabouWhile = false;

//        try {
            // coloca os elementos da fila recebida na fila de execução interna do while
            while (!acabouWhile) {
                Object elementoFilaExecucao = filaComandoAtual.removeFila();
                filaComandosWhile.insereFila(elementoFilaExecucao);
                if (((String)elementoFilaExecucao).equals("endwhile")) {
                    pilhaWhile.desempilha();
                    if (pilhaWhile.pilhaVazia())
                        acabouWhile = true;
                } else if (((String)elementoFilaExecucao).equals("while")) {
                    pilhaWhile.empilha(elementoFilaExecucao);
                }
            }

            // executa o while
            boolean continuaWhile = true;
            Fila filaExecucao = filaComandosWhile;
            while (continuaWhile) {
                // analisa expressão
                    // se retornar true, continua rodando o while
//                if ( resultadoExpressao ) {
                Object temp = null;     
                while (!filaExecucao.filaVazia()) {
//                    temp = filaExecucao.removeFila();
//                    if (Dicionarios.procuraElementoNoDicionario((String)temp, Dicionarios.LISTA_COMANDOS)) {
//                         é um comando
//                        Comando comando;
//                        Object elementoFilaExecucao;
//                        switch (Dicionarios.qualComando((String)temp)) {
//                            // print
//                            case 0:
//                                comando = new ComandoPrint((String)filaExecucao.removeFila());
//                                break;
//                            // println
//                            case 1:
//                                comando = new ComandoPrintln((String)filaExecucao.removeFila());
//                                break;
//                            // "readInt",    2
//                            case 2:
//                                comando = new ComandoReadInt((String)filaExecucao.removeFila());
//                                break;
//                            // "if",         3
//                            case 3:                            
//                                Pilha pilhaIf = new Pilha();
//                                pilhaIf.empilha(temp);
//
//                                Fila filaComandoIf = new Fila();
//                                filaComandoIf.insereFila(temp);
//
//                                boolean acabouIf = false;
//
//                                while (!acabouIf) {
//                                    elementoFilaExecucao = filaExecucao.removeFila();
//                                    filaComandoIf.insereFila(elementoFilaExecucao);
//                                    if (((String)elementoFilaExecucao).equals("endif")) {
//                                        pilhaIf.desempilha();
//                                        if (pilhaIf.pilhaVazia())
//                                            acabouIf = true;
//                                    } else if (((String)elementoFilaExecucao).equals("if")) {
//                                        pilhaIf.empilha(elementoFilaExecucao);
//                                    }
//                                }
//
//                                comando = new ComandoIf(filaComandoIf);
//
//                                break;
//                            // "while",      7
//                            case 7:
//                                Pilha pilhaWhileInterno = new Pilha();
//                                pilhaWhileInterno.empilha(temp);
//
//                                Fila filaComandoWhile = new Fila();
//                                filaComandoWhile.insereFila(temp);
//
//                                boolean acabouWhileInterno = false;
//
//                                while (!acabouWhile) {
//                                    elementoFilaExecucao = filaExecucao.removeFila();
//                                    filaComandoWhile.insereFila(elementoFilaExecucao);
//                                    if (((String)elementoFilaExecucao).equals("endwhile")) {
//                                        pilhaWhileInterno.desempilha();
//                                        if (pilhaWhileInterno.pilhaVazia())
//                                            acabouWhile = true;
//                                    } else if (((String)elementoFilaExecucao).equals("while")) {
//                                        pilhaWhileInterno.empilha(elementoFilaExecucao);
//                                    }
//                                }
//
//                                comando = new ComandoWhile(filaComandoWhile);
//
//                                break;
//                            //"for",        10
//                            case 10:
//                                Pilha pilhaFor = new Pilha();
//                                pilhaFor.empilha(temp);
//
//                                Fila filaComandoFor = new Fila();
//                                filaComandoFor.insereFila(temp);
//
//                                boolean acabouFor = false;
//
//                                while (!acabouFor) {
//                                    elementoFilaExecucao = filaExecucao.removeFila();
//                                    filaComandoFor.insereFila(elementoFilaExecucao);
//                                    if (((String)elementoFilaExecucao).equals("endfor")) {
//                                        pilhaFor.desempilha();
//                                        if (pilhaFor.pilhaVazia())
//                                            acabouFor = true;
//                                    } else if (((String)elementoFilaExecucao).equals("for")) {
//                                        pilhaFor.empilha(elementoFilaExecucao);
//                                    }
//                                }
//
//                                comando = new ComandoFor(filaComandoFor);
//
//                                break;
//                            // end
//                            case 14:
//                                System.exit(0);
//                                break;
//                        }
//                    } else {
//                        // atribuição
//                        Fila filaComandoAtribuicao = new Fila();
//
//                        // nome da variável
//                        filaComandoAtribuicao.insereFila(temp);
//
//                        // atribuidor
//                        temp = filaExecucao.removeFila();
//                        filaComandoAtribuicao.insereFila(temp);
//
//                        // valor da variável
//                        temp = filaExecucao.removeFila();
//                        filaComandoAtribuicao.insereFila(temp);
//
//                        Comando comando = new ComandoAtribuicao(filaComandoAtribuicao);
//                    }
//                } else {
//                    continuaWhile = false;
//                }
//            }

//        } catch (ExcecaoFilaVazia ex) {
//            System.err.println(ex.getMessage());
//        } catch (ExcecaoPilhaVazia ex) {
//            System.err.println(ex.getMessage());
//        } catch (ExcecaoExpressaoInvalida ex) {
//            System.err.println(ex.getMessage());
        }
        
    }
//
//
//
}
//
//
//
    @Override
    public void analisa() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        // recebeu uma fila
        Object temp;
        String parametro;
        Pilha pBase;

        // temp = while
        temp = filaComandoAtual.removeFila();

        // temp = expressao
        temp = filaComandoAtual.removeFila();

        parametro = temp.toString();
        parametro = parametro.substring(1, (parametro.length() - 1));
        
        expressaoWhile = parametro;
        
        pBase = Calculadora.formataAnalisaExpressao(expressaoWhile);

        if ( !AnalisadorExpressao.tipoExpressao(pBase).equals("l") 
             || !parametro.equals("true") || !parametro.equals("false")) {
            throw new ExcecaoExpressaoInvalida("Comando 'while': possui resultado não booleano");
        }
        
        // temp = do
        temp = filaComandoAtual.removeFila();
        
    }
    
}
