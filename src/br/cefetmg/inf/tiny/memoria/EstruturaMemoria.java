package br.cefetmg.inf.tiny.memoria;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.util.Dicionarios;

public class EstruturaMemoria<Dado> {

    private static EstruturaMemoria instancia;
    protected Variavel primeiraVar;
    protected Variavel ultimaVar;

    private EstruturaMemoria() {
        this.primeiraVar = null;
        this.ultimaVar = null;
    }

    public static synchronized EstruturaMemoria getInstancia() {
        if (instancia == null) {
            instancia = new EstruturaMemoria();
        }
        return instancia;
    }

    public void armazenaVariavel(String nomeVar, Dado valorVar) throws ExcecaoExpressaoInvalida {
        if (!Dicionarios.procuraElementoNoDicionario(nomeVar, Dicionarios.LISTA_COMANDOS) &&
            !Dicionarios.procuraElementoNoDicionario(nomeVar, Dicionarios.OP_ARITMETICOS) &&
            !Dicionarios.procuraElementoNoDicionario(nomeVar, Dicionarios.OP_LOGICOS))       {
            if (listaVazia()) {
                // se a lista estiver vazia, essa é a primeira variável a ser armazenada (e a ultima)
                primeiraVar = new Variavel(nomeVar, valorVar, determinaTipo(valorVar));
                ultimaVar = primeiraVar;
                // a lista não está vazia
            } else {
                    if (procuraVariavel(nomeVar) == null) {
                        // essa variável ainda nao existe, deve ser criada
                        ultimaVar.setProxNodo(new Variavel(nomeVar, valorVar, determinaTipo(valorVar)));
                        Variavel temp = ultimaVar;
                        ultimaVar = temp.getProxNodo();
                    } else {
                        // a variável já existe. seu valor deve ser apenas alterado
                        alteraValorVariavel(nomeVar, valorVar);
                    }
            }
        } else {
            throw new ExcecaoExpressaoInvalida ("Nome de variável inválido");
        }
    }

    public Variavel procuraVariavel(String nomeVar) {
        if (!listaVazia()) {
            // se a lista não estiver vazia, procura a variável na "memória"
            Variavel temp = primeiraVar;
            while (temp != null) {
                // enquanto não tiver percorrido a lista toda
                if (nomeVar.equals(temp.getNome())) {
                    // encontrou a variável e retorna a Variavel que a representa
                    return temp;
                } else {
                    // não encontrou a variável ainda, continua procurando
                    temp = temp.getProxNodo();
                }
            }
        }
        // retorna nulo: a variável não foi encontrada na memória
        return null;
    }

    public void alteraValorVariavel(String nomeVar, Dado valorVar) throws ExcecaoExpressaoInvalida {
        // procura a variável na memória
        Variavel varAlterada = procuraVariavel(nomeVar);
        // troca o valor armazenado

        varAlterada.setConteudo(valorVar);
        varAlterada.setTipo(determinaTipo(valorVar));

//        System.out.println("variável alterada! " + varAlterada.getNomeVar() + " agora é igual a " + varAlterada.getDado());
    }

    public void removeVariavel(String nomeVar) {
        if (!listaVazia()) {
            // a lista não está vazia: há variáveis que podem ser removidas
            Variavel temp = primeiraVar;
            if ((temp.getNome()).equals(nomeVar)) {
                // se a variável a ser removida for a primeira
                primeiraVar = temp.getProxNodo();
            } else {
                while (temp.getProxNodo() != null) {
                    // enquanto não tiver percorrido a lista toda
                    if (((temp.getProxNodo()).getNome()).equals(nomeVar)) {
                        // testa se a variável que será removida é a posterior ao nodo atual
                        if ((temp.getProxNodo()).equals(ultimaVar)) {
                            // se a variável a ser removida for a última, a anterior a ela deve se tornar a última
                            ultimaVar = temp;
                        }

//                        System.out.println("variável " + temp.getProxNodo().getNomeVar() + " removida");
                        // "pula" um nodo -> não será acessado mais, variável excluída da lista
                        temp.setProxNodo((temp.getProxNodo()).getProxNodo());

//                        System.out.println("variável " + temp.getNomeVar() + " agr é sucedida por " + temp.getProxNodo().getNomeVar());
                        return;
                    } else {
                        temp = temp.getProxNodo();
                    }
                }
            }
        } else {
//            System.out.println("Não é possível remover a variável. A memória está vazia!");
        }
    }

    private String determinaTipo(Dado valorVar) {
        String tipoVar;

        if (valorVar instanceof Integer) {
            tipoVar = "int";
        } else if (valorVar instanceof Double) {
            tipoVar = "double";
        } else if (valorVar.toString().startsWith("\"") && valorVar.toString().endsWith("\"")) {
            tipoVar = "string";
        } else if (valorVar instanceof String) {
            tipoVar = "expressao";
        } else {
            tipoVar = "boolean";
        }
        return tipoVar;
    }

    public boolean listaVazia() {
        return (primeiraVar == null);
    }

    public Variavel getPrimeiraVar() {
        return primeiraVar;
    }

    public void setPrimeiraVar(Variavel primeiraVar) {
        this.primeiraVar = primeiraVar;
    }

    public Variavel getUltimaVar() {
        return ultimaVar;
    }

    public void setUltimaVar(Variavel ultimaVar) {
        this.ultimaVar = ultimaVar;
    }
}
