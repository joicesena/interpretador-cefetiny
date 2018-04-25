package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.AnalisadorExpressao;
import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.executor.Executor;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;

public class ComandoFor extends Comando{
    private static EstruturaMemoria variaveis;
    private int atribuicaoFor;
    private String nomeVar;
    private String tipoOperacao;
    private int valorComparacao;
    private Fila filaComandosFor;

    public ComandoFor(String parametro) {
        super(parametro);
    }

    public ComandoFor(Fila filaComandoAtual) {
        super(filaComandoAtual);
        variaveis = EstruturaMemoria.getInstancia();

        filaComandosFor = new Fila();
        
        try {
            analisa();
            executaComando();
            variaveis.removeVariavel(nomeVar);
        } catch (ExcecaoFilaVazia | ExcecaoExpressaoInvalida | ExcecaoPilhaVazia ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void executaComando()  throws ExcecaoFilaVazia, ExcecaoPilhaVazia, ExcecaoExpressaoInvalida{
        Pilha pilhaFor = new Pilha();
        pilhaFor.empilha("for");

        boolean acabouFor = false;
        Object elementoFila;

        try {
            // coloca os elementos da fila recebida na fila de execução interna do for

            while (!acabouFor) {
                elementoFila = filaComandoAtual.removeFila();
                filaComandosFor.insereFila(elementoFila);
                if (((String)elementoFila).equals("endfor")) {
                    pilhaFor.desempilha();
                    if (pilhaFor.pilhaVazia())
                        acabouFor = true;
                } else if (((String)elementoFila).equals("for")) {
                    pilhaFor.empilha(elementoFila);
                }
            }

            Fila filaExecucao = new Fila();
            
            // executa o for
            boolean continuaFor = true;
            while (continuaFor) {
                copiaFila(filaComandosFor, filaExecucao);
                Executor.executaPrograma(filaExecucao);
                if (tipoOperacao.equals("to")) {
                    atribuicaoFor++;
                } else {
                    atribuicaoFor--;
                }
                if (atribuicaoFor == valorComparacao) {
                    continuaFor = false;
                }
                variaveis.alteraValorVariavel(nomeVar, atribuicaoFor);
            }
            
        } catch (ExcecaoFilaVazia ex) {
            System.err.println(ex.getMessage());
        } catch (ExcecaoPilhaVazia ex) {
            System.err.println(ex.getMessage());
//        } catch (ExcecaoExpressaoInvalida ex) {
//            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        // remove for
        Object temp = filaComandoAtual.removeFila();
        
        // temp = nome da variável
        temp = filaComandoAtual.removeFila();
        if (!AnalisadorExpressao.nomeVariavelProcede(temp.toString())) {
            throw new ExcecaoExpressaoInvalida("Comando 'for': nome de variável na atribuição é inválido");
        }
        nomeVar = temp.toString();

        //temp = :=
        temp = filaComandoAtual.removeFila();

        //temp = valorVar
        temp = filaComandoAtual.removeFila();
        
        Object resultadoExpressao = ((Calculadora.iniciaCalculadora(temp.toString())));
        
        if ( !(resultadoExpressao instanceof Integer)) { 
            throw new ExcecaoExpressaoInvalida("Comando 'for': valor atribuído à variável não é inteiro");
        }
        atribuicaoFor = (int) resultadoExpressao;

        variaveis.armazenaVariavel(nomeVar, atribuicaoFor);

        //temp = to || downto
        temp = filaComandoAtual.removeFila();
        tipoOperacao = temp.toString();

        //temp = condição parada
        temp = filaComandoAtual.removeFila();
        resultadoExpressao = ((Calculadora.iniciaCalculadora(temp.toString())));
        
        if (resultadoExpressao instanceof Integer) {
            valorComparacao = (Integer)resultadoExpressao;
        } else {
            throw new ExcecaoExpressaoInvalida("Comando 'for': condição de parada não é inteiro");
        }
        
        if (tipoOperacao.equals("to")) {
            if (atribuicaoFor > valorComparacao) {
                throw new ExcecaoExpressaoInvalida("Comando 'for': valor da condição de parada deveria ser maior que o valor da variável");
            }
        } else if (tipoOperacao.equals("downto")) {
            if (atribuicaoFor < valorComparacao) {
                throw new ExcecaoExpressaoInvalida("Comando 'for': valor da condição de parada deveria ser menor que o valor da variável");
            }
        }
        //temp = do
        temp = filaComandoAtual.removeFila();
    }

    public void copiaFila (Fila filaOriginal, Fila filaCopia) throws ExcecaoFilaVazia {
        Fila filaTemp = new Fila();
        Object temp;
        while (!filaOriginal.filaVazia()) {
            temp = filaOriginal.removeFila();
            filaCopia.insereFila(temp);
            filaTemp.insereFila(temp);
        }
        while (!filaTemp.filaVazia()) {
            temp = filaTemp.removeFila();
            filaOriginal.insereFila(temp);
        }
    }

    
}
