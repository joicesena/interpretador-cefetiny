package br.cefetmg.inf.tiny.estruturasDados.pilhaDinamica;

import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;

public class Pilha implements MetodosPilha {

    private Nodo topo;

    @Override
    public void empilha(Object conteudo) throws ExcecaoPilhaVazia {
        Nodo novo = new Nodo(conteudo, topo);
        topo = novo;
    }

    @Override
    public Object desempilha() throws ExcecaoPilhaVazia {
        Object antigoTopo;

        if (this.estaVazia() == false) {
            antigoTopo = topo.getConteudo();
            topo = topo.getProximo();

            return antigoTopo;
        } else {
            throw new ExcecaoPilhaVazia();
        }
    }

    @Override
    public boolean estaVazia() {
        if (topo == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int tamanhoPilha() {
        int contadorTamanho = 0;
        Nodo percorre;
        percorre = topo;

        while (percorre != null) {
            percorre = percorre.getProximo();
            contadorTamanho++;
        }
        return contadorTamanho;
    }

    @Override
    public void imprimePilha() throws ExcecaoPilhaVazia {
        Nodo percorre = topo;
        String saida = "";
        if (this.estaVazia() == false) {
            while (percorre != null) {
                saida += percorre.getConteudo();
                percorre = percorre.getProximo();
            }
            System.out.println(saida);
        } else {
            throw new ExcecaoPilhaVazia();
        }

    }

    @Override
    public void invertePilha() throws ExcecaoPilhaVazia {
        Object[] vetorInvertido = new Object[this.tamanhoPilha()];
        Object elementoSaida;
        int i = 0;

        if (this.estaVazia() == false) {
            while (i < vetorInvertido.length) {
                elementoSaida = this.desempilha();
                vetorInvertido[i] = elementoSaida;
                i++;
            }
            i = 0;
            while (i < vetorInvertido.length) {
                this.empilha(vetorInvertido[i]);
                i++;
            }
        } else {
            throw new ExcecaoPilhaVazia();
        }
    }

    @Override
    public void esvaziaPilha() {
        this.topo = null;
    }
}
