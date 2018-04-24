package br.cefetmg.inf.tiny.analisador.analisadorSemantico;

import br.cefetmg.inf.calculadora.AnalisadorExpressao;
import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.analisador.Analisador;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.util.Conversor;

public final class AnalisadorSemantico extends Analisador {

    private static AnalisadorSemantico instancia;

    private static EstruturaMemoria variaveis;

    private static Fila filaAux;

    private static Object elementoAtual;

    private static Pilha pBase;

    private static String parametro;
    private static String parametro2;
    private static String parametro3;

    private AnalisadorSemantico() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        variaveis = EstruturaMemoria.getInstancia();

        filaAux = new Fila();

        pBase = new Pilha();

        this.analisa();
    }

    public static synchronized AnalisadorSemantico getInstancia() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        if (instancia == null) {
            instancia = new AnalisadorSemantico();
        }
        return instancia;
    }

    public void analisa() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        do {
            transfereElemento();
            //
            if (elementoAtual.equals("print")) {
                this.analisaPrint();
            } else if (elementoAtual.equals("println")) {
                this.analisaPrint();
            } else if (elementoAtual.equals("readInt")) {
                this.analisaReadInt();
            } else if (elementoAtual.equals("if")) {
                this.analisaIf();
            } else if (elementoAtual.equals("else")) {
                this.analisaElse();
            } else if (elementoAtual.equals("endif")) {
                this.analisaEndif();
            } else if (elementoAtual.equals("while")) {
                this.analisaWhile();
            } else if (elementoAtual.equals("endwhile")) {
                this.analisaEndwhile();
            } else if (elementoAtual.equals("for")) {
                this.analisaFor();
            } else if (elementoAtual.equals("endfor")) {
                this.analisaEndfor();
            } else if (elementoAtual.equals("end")) {
                break;
            } else {
                this.analisaAtribuicao();
            }
            
        } while (!filaExecucao.filaVazia());
    }

    @Override
    public void analisaPrint() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        //parâmetro
        transfereElemento();
        
        parametro = elementoAtual.toString();
        parametro = parametro.substring(1, (parametro.length() - 1));
        pBase = Calculadora.formataAnalisaExpressao(parametro);
    }

    @Override
    public void analisaReadInt() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        //parâmetro
        transfereElemento();

        parametro = elementoAtual.toString();
        parametro = parametro.substring(1, (parametro.length() - 1));
        pBase = Calculadora.formataAnalisaExpressao(parametro);
    }

    @Override
    public void analisaIf() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        //parâmetro
        transfereElemento();

        parametro = elementoAtual.toString();
        parametro = parametro.substring(1, (parametro.length() - 1));
        pBase = Calculadora.formataAnalisaExpressao(parametro);

        if (!AnalisadorExpressao.tipoExpressao(pBase).equals("l") || parametro.equals("true")
            || parametro.equals("false")) {
            throw new ExcecaoExpressaoInvalida("Comando 'if': possui resultado não booleano");
        }
        //then
        transfereElemento();
    }

    @Override
    public void analisaElse() {
    }

    @Override
    public void analisaEndif() {
    }

    @Override
    public void analisaWhile() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        //parâmetro
        transfereElemento();

        parametro = elementoAtual.toString();
        parametro = parametro.substring(1, (parametro.length() - 1));
        pBase = Calculadora.formataAnalisaExpressao(parametro);

        if (!AnalisadorExpressao.tipoExpressao(pBase).equals("l") || parametro.equals("true")
            || parametro.equals("false")) {
            throw new ExcecaoExpressaoInvalida("Comando 'while': possui resultado não booleano");
        }
        
        //do
        transfereElemento();
    }

    @Override
    public void analisaEndwhile() {
    }

    @Override
    public void analisaFor() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        //var
        transfereElemento();

        if (!AnalisadorExpressao.nomeVariavelProcede(elementoAtual.toString())) {
            throw new ExcecaoExpressaoInvalida("Comando 'for': nome de variável na atribuição é inválido");
        }

        //:=
        transfereElemento();

        //valorVar
        transfereElemento();
        if (!(elementoAtual instanceof Integer) && variaveis.procuraVariavel(elementoAtual.toString()) == null) {
            throw new ExcecaoExpressaoInvalida("Comando 'for': valor atribuído à variável não é inteiro");
        }
        int parametro1For = (int) elementoAtual;

        //to || downto
        transfereElemento();
        String parametro2For = elementoAtual.toString();

        //condição parada
        transfereElemento();
        if (!(elementoAtual instanceof Integer) && variaveis.procuraVariavel(elementoAtual.toString()) == null) {
            throw new ExcecaoExpressaoInvalida("Comando 'for': condição de parada não é inteiro");
        }

        if (parametro2For.equals("to")) {
            if (parametro1For > (int) elementoAtual) {
                throw new ExcecaoExpressaoInvalida("Comando 'for': valor da condição de parada deveria ser maior que o valor da variável");
            }
        } else if (parametro2For.equals("downto")) {
            if (parametro1For < (int) elementoAtual) {
                throw new ExcecaoExpressaoInvalida("Comando 'for': valor da condição de parada deveria ser menor que o valor da variável");
            }
        }
        //do
        transfereElemento();
    }

    @Override
    public void analisaEndfor() {
    }

    @Override
    public void analisaAtribuicao() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        //variável para atribuição
        if (!AnalisadorExpressao.nomeVariavelProcede(elementoAtual.toString())) {
            throw new ExcecaoExpressaoInvalida("Comando de atribuição: nome de variável na atribuição é inválido");
        }
        String nomeVar = elementoAtual.toString();
        
        //:=
        transfereElemento();
        
        //valorVar
        transfereElemento();
        variaveis.armazenaVariavel(nomeVar, Calculadora.iniciaCalculadora(elementoAtual.toString()));
    }

    @Override
    public void analisaEnd() {
    }

    private static void transfereElemento() throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        elementoAtual = filaExecucao.removeFila();
        elementoAtual = Conversor.converteStringNumero((String) elementoAtual);
        filaAux.insereFila(elementoAtual);
    }
}
