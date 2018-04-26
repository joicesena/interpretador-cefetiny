package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.excecoes.ExcecaoEntradaInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.executor.Executor;

public final class ComandoIf extends Comando{
    Fila executaThen;
    Fila executaElse;
    Pilha pilhaIf;
    String expressaoIf = null;
  
    public ComandoIf(Fila filaComandoAtual) throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoEntradaInvalida {
        super(filaComandoAtual);
        this.analisa();
        this.executaComando();
    }

    @Override
    public void executaComando() throws ExcecaoExpressaoInvalida, ExcecaoFilaVazia, ExcecaoPilhaVazia, ExcecaoEntradaInvalida {
        if (((Calculadora.iniciaCalculadora(expressaoIf)).toString()).equals("true")) {
            Executor.executaPrograma(executaThen);
        } else {
            Executor.executaPrograma(executaElse);
        }
    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoEntradaInvalida {
        pilhaIf = new Pilha();
        executaThen = new Fila();
        executaElse = new Fila();
        Object tempPilha;
        Object tempFila;
        boolean flagElse = false;
        
        // pilhaIf recebe if
        pilhaIf.empilha(filaComandoAtual.removeFila());
   
        // tempFila = expressao
        tempFila = filaComandoAtual.removeFila();
        
        expressaoIf = tempFila.toString();
        expressaoIf = expressaoIf.substring(1, expressaoIf.length()-1);
        
        while (!filaComandoAtual.filaVazia()){
            tempFila = filaComandoAtual.removeFila();
            
            if((tempFila.toString()).equals("if")){
                pilhaIf.empilha(tempFila);
                if (!flagElse){
                    executaThen.insereFila(tempFila);
                } else {
                    executaElse.insereFila(tempFila);
                } 
                
            } else if((tempFila.toString()).equals("else")){
                tempPilha = pilhaIf.desempilha();
                if(pilhaIf.pilhaVazia()){
                    flagElse = true;
                    pilhaIf.empilha(tempFila);
                } else {
                    pilhaIf.empilha(tempPilha);
                    if (!flagElse){
                        executaThen.insereFila(tempFila);
                    } else {
                        executaElse.insereFila(tempFila);
                    } 
                }
                
            } else if((tempFila.toString()).equals("endif")) {
                pilhaIf.desempilha();
                if(!pilhaIf.pilhaVazia()){
                    if (!flagElse){
                        executaThen.insereFila(tempFila);
                    } else {
                        executaElse.insereFila(tempFila);
                    } 
                } else if(!filaComandoAtual.filaVazia()) {
                    Executor.executaPrograma(filaComandoAtual);
                }  
                
            } else {
                if (!flagElse){
                    executaThen.insereFila(tempFila);
                } else {
                    executaElse.insereFila(tempFila);
                } 
                
            }
        }
       
    }
    
}
