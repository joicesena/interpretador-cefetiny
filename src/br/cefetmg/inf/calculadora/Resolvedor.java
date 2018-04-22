package br.cefetmg.inf.calculadora;

import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public abstract class Resolvedor {
    protected static Object operando1, operando2;
    protected static Object elementoAtual;

    protected static String operador;

    public static Object resolveExpressaoCompleta(Pilha pBase) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        Pilha pResolvida = ResolvedorAritmetico.resolveExpressaoAritmetica(pBase);

        Object decisao = pResolvida.desempilha();

        if (decisao.equals("&")) {
            pResolvida = ResolvedorLogico.resolveExpressaoLogica(pBase);
        } else {
            pResolvida.empilha(decisao);
        }
        return pResolvida.desempilha();
    }
}

//25+(14+(25*4+40-(20/2+10)))
