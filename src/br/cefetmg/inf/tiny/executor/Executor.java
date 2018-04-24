package br.cefetmg.inf.tiny.executor;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.executor.comandos.*;
import br.cefetmg.inf.util.Dicionarios;

/**
 * tirar da fila de execução principal e adicionar numa fila temporária do comando
 * passar essa fila temporária como parâmetro pro comando
 */
 
public final class Executor {
    private static Fila filaExecucao;
    
    public static void executaPrograma(Fila filaAnalisada) throws ExcecaoExpressaoInvalida {
        filaExecucao = filaAnalisada;
        try {
            Object temp = null;        
            while (!filaExecucao.filaVazia()) {
                temp = filaExecucao.removeFila();
                if (Dicionarios.procuraElementoNoDicionario((String)temp, Dicionarios.LISTA_COMANDOS)) {
                    // é um comando
                    Comando comando;
                    Object elementoFilaExecucao;
                    switch (Dicionarios.qualComando((String)temp)) {
                        // print
                        case 0:
                            comando = new ComandoPrint(filaExecucao.removeFila().toString());
                            break;
                        // println
                        case 1:
                            comando = new ComandoPrintln(filaExecucao.removeFila().toString());
                            break;
                        //"readInt",    2
                        case 2:
                            comando = new ComandoReadInt((String)filaExecucao.removeFila().toString());
                            break;
                        //"if",         3
                        case 3:                            
                            Pilha pilhaIf = new Pilha();
                            pilhaIf.empilha(temp);
                            
                            Fila filaComandoIf = new Fila();
                            filaComandoIf.insereFila(temp);
                            
                            boolean acabouIf = false;
                            
                            while (!acabouIf) {
                                elementoFilaExecucao = filaExecucao.removeFila();
                                filaComandoIf.insereFila(elementoFilaExecucao);
                                if (((String)elementoFilaExecucao).equals("endif")) {
                                    pilhaIf.desempilha();
                                    if (pilhaIf.pilhaVazia())
                                        acabouIf = true;
                                } else if (((String)elementoFilaExecucao).equals("if")) {
                                    pilhaIf.empilha(elementoFilaExecucao);
                                }
                            }
                            
                            comando = new ComandoIf(filaComandoIf);
                            
                            break;
                        //"while",      7
                        case 7:
                            Pilha pilhaWhile = new Pilha();
                            pilhaWhile.empilha(temp);
                            
                            Fila filaComandoWhile = new Fila();
                            filaComandoWhile.insereFila(temp);
                            
                            boolean acabouWhile = false;
                            
                            while (!acabouWhile) {
                                elementoFilaExecucao = filaExecucao.removeFila();
                                filaComandoWhile.insereFila(elementoFilaExecucao);
                                if (((String)elementoFilaExecucao).equals("endwhile")) {
                                    pilhaWhile.desempilha();
                                    if (pilhaWhile.pilhaVazia()) {
                                        acabouWhile = true;
                                    }
                                } else if (((String)elementoFilaExecucao).equals("while")) {
                                    pilhaWhile.empilha(elementoFilaExecucao);
                                }
                            }
                            
                            comando = new ComandoWhile(filaComandoWhile);
                            
                            break;
                        //"for",        10
                        case 10:
                            Pilha pilhaFor = new Pilha();
                            pilhaFor.empilha(temp);
                            
                            Fila filaComandoFor = new Fila();
                            filaComandoFor.insereFila(temp);
                            
                            boolean acabouFor = false;
                            
                            while (!acabouFor) {
                                elementoFilaExecucao = filaExecucao.removeFila();
                                filaComandoFor.insereFila(elementoFilaExecucao);
                                if (((String)elementoFilaExecucao).equals("endfor")) {
                                    pilhaFor.desempilha();
                                    if (pilhaFor.pilhaVazia())
                                        acabouFor = true;
                                } else if (((String)elementoFilaExecucao).equals("for")) {
                                    pilhaFor.empilha(elementoFilaExecucao);
                                }
                            }
                            
                            comando = new ComandoFor(filaComandoFor);
                            
                            break;
                        // end
                        case 14:
                            System.exit(0);
                            break;
                    }
                } else {
                    // atribuição
                    Fila filaComandoAtribuicao = new Fila();

                    // nome da variável
                    filaComandoAtribuicao.insereFila(temp);

                    // atribuidor
                    temp = filaExecucao.removeFila();

                    // valor da variável
                    temp = filaExecucao.removeFila();
                    filaComandoAtribuicao.insereFila(temp);
                    
                    Comando comando = new ComandoAtribuicao(filaComandoAtribuicao);
                }
            }
        } catch (ExcecaoFilaVazia ex) {
            System.err.println(ex.getMessage());
        } catch (ExcecaoPilhaVazia ex) {
            System.err.println(ex.getMessage());
        }
    }
}
