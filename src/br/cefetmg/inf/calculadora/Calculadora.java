package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.memoria.Variavel;
import br.cefetmg.inf.util.Conversor;
import java.util.Scanner;

public final class Calculadora {

    private static EstruturaMemoria variaveis;

    private static Object resultadoExpressao;

    static Object iniciaCalculadora(String expressaoBase) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        variaveis = EstruturaMemoria.getInstancia();

        Object resultadoFinal = null;
        //
        //try {
            resultadoFinal = preparaPilha(expressaoBase);
        //} catch (ExcecaoExpressaoInvalida | ExcecaoPilhaVazia e) {
          //  System.err.println(e.getMessage());
            //System.exit(0);
        //}
        return resultadoFinal;
    }

    private static Object preparaPilha(String expressaoBase) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        Pilha pOrganizada = new Pilha();
        //
        pOrganizada = formataAnalisaExpressao(expressaoBase);

        if (pOrganizada.tamanhoPilha() == 1) {
            return resolvePilhaTamanhoUnitario(pOrganizada);
        }

        return encontraParenteses(pOrganizada);
    }

    public static Pilha formataAnalisaExpressao(String expressaoBase) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        Pilha pAnalisada = new Pilha();
        //
        pAnalisada = Organizador.organizaExpressaoNaPilha(expressaoBase);
        pAnalisada.invertePilha();

        pAnalisada = AnalisadorExpressao.inicializaAutomato(pAnalisada);
        pAnalisada.invertePilha();

        return pAnalisada;
    }

    private static Object resolvePilhaTamanhoUnitario(Pilha pUnitaria) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        Object resultado = null;
        //
        if ((variaveis.procuraVariavel(pUnitaria.getTopo().getConteudo().toString())) != null) {
            Variavel variavel = variaveis.procuraVariavel(pUnitaria.getTopo().getConteudo().toString());

            if (variavel.getTipo().equals("int") || variavel.getTipo().equals("double")
                || variavel.getTipo().equals("expressao")) {
                resultado = Resolvedor.resolveExpressaoCompleta(pUnitaria);
            } else if (variavel.getTipo().equals("string")) {
                resultado = variavel.getConteudo();
            }
        } else if (pUnitaria.getTopo().getConteudo() instanceof Integer || pUnitaria.getTopo().getConteudo() instanceof Double) {
            resultado = Conversor.converterObjectParaInt(pUnitaria.getTopo().getConteudo());
        } else {
            resultado = pUnitaria.getTopo().getConteudo();
        }
        return resultado;
    }

    private static Object encontraParenteses(Pilha pBase) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        int contaAbre = 0;
        int contaFecha = 0;

        Object elementoSaida;

        Pilha pAux1 = new Pilha();
        Pilha pAux2 = new Pilha();
        //
        do {
            elementoSaida = pBase.desempilha();

            if (elementoSaida.equals("(")) {
                preparaExpressao(pBase, pAux2, contaFecha, contaAbre);

            } else {
                pAux1.empilha(elementoSaida);
            }
            if (pBase.pilhaVazia() == true
                && pAux2.pilhaVazia() == true
                && pAux1.tamanhoPilha() > 1) {
                encontraResultadoExpressao(pAux1);
            }
        } while (pBase.pilhaVazia() == false);

        return pAux1.desempilha();
    }

    private static void preparaExpressao(Pilha pBase, Pilha pAux2, int contaFecha, int contaAbre) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        contaAbre++;

        Object elementoSaida2;
        //
        do {
            elementoSaida2 = pBase.desempilha();

            if (elementoSaida2.equals("(")) {
                pAux2.empilha(elementoSaida2);
                contaAbre++;
            } else if (elementoSaida2.equals(")")) {
                contaFecha++;
                if (contaAbre != contaFecha) {
                    pAux2.empilha(elementoSaida2);
                }
            } else {
                pAux2.empilha(elementoSaida2);
            }
        } while (contaAbre != contaFecha);

        pAux2.invertePilha();
        pBase.empilha(encontraParenteses(pAux2));
        pAux2.esvaziaPilha();
    }

    private static void encontraResultadoExpressao(Pilha pAux1) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        pAux1.invertePilha();
        resultadoExpressao = Resolvedor.resolveExpressaoCompleta(pAux1);
        pAux1.esvaziaPilha();
        pAux1.empilha(resultadoExpressao);
    }
}
