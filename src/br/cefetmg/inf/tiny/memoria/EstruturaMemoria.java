package br.cefetmg.inf.tiny.memoria;

public class EstruturaMemoria<T> {
    protected VariavelArmazenada primeiraVar;
    protected VariavelArmazenada ultimaVar;

    public EstruturaMemoria() {
        this.primeiraVar = null;
        this.ultimaVar = null;
    }
    
    public void armazenaVariavel (String nomeVar, T valorVar) {
        if (listaVazia()) {
            // se a lista estiver vazia, essa é a primeira variável a ser armazenada (e a ultima)
            primeiraVar = new VariavelArmazenada(nomeVar, valorVar);
            ultimaVar = primeiraVar;
            
//            System.out.println("variável armazenada! " + primeiraVar.getNomeVar() + " := " + primeiraVar.getDado());

        } else {
            // a lista não está vazia
            if (procuraVariavel(nomeVar) == null) {
                // essa variável ainda nao existe, deve ser criada
                ultimaVar.setProxNodo(new VariavelArmazenada(nomeVar, valorVar));
                VariavelArmazenada temp = ultimaVar;
                ultimaVar = temp.getProxNodo();
    
//                System.out.println("variável armazenada! " + ultimaVar.getNomeVar() + " := " + ultimaVar.getDado());
            
            } else {
                // a variável já existe. seu valor deve ser apenas alterado
                alteraValorVariavel(nomeVar, valorVar);
            }
        }
    }
    
    public VariavelArmazenada procuraVariavel (String nomeVar) {
        if ( !listaVazia() ) {
            // se a lista não estiver vazia, procura a variável na "memória"
            VariavelArmazenada temp = primeiraVar;
            while (temp.getProxNodo() != null) {
                // enquanto não tiver percorrido a lista toda
                if (nomeVar.equals(temp.getNomeVar())) {
                    // encontrou a variável e retorna a Variavel que a representa
                    return temp;
                } else {
                    // não encontrou a variável ainda, continua procurando
                    temp = temp.getProxNodo();
                }
            }
        } 
//        System.out.println("Não foi possível encontrar a variável.");
        // retorna nulo: a variável não foi encontrada na memória
        return null;
    }
    
    public void alteraValorVariavel(String nomeVar, T valorVar) {
        // procura a variável na memória
        VariavelArmazenada varAlterada = procuraVariavel(nomeVar);
        // troca o valor armazenado
        varAlterada.setDado(valorVar);

//        System.out.println("variável alterada! " + varAlterada.getNomeVar() + " agora é igual a " + varAlterada.getDado());

    }
    
    public void removeVariavel (String nomeVar) {
        if (!listaVazia()) {
            // a lista não está vazia: há variáveis que podem ser removidas
            VariavelArmazenada temp = primeiraVar;
            if ((temp.getNomeVar()).equals(nomeVar)) {
                // se a variável a ser removida for a primeira
                primeiraVar = temp.getProxNodo();                
            } else {
                while (temp.getProxNodo() != null) {
                    // enquanto não tiver percorrido a lista toda
                    if ( ((temp.getProxNodo()).getNomeVar()).equals(nomeVar) ) {
                        // testa se a variável que será removida é a posterior ao nodo atual
                        if ((temp.getProxNodo()).equals(ultimaVar)) {
                            // se a variável a ser removida for a última, a anterior a ela deve se tornar a última
                            ultimaVar = temp;
                        }

//                        System.out.println("variável " + temp.getProxNodo().getNomeVar() + " removida");

                        // "pula" um nodo -> não será acessado mais, variável excluída da lista
                        temp.setProxNodo( (temp.getProxNodo()).getProxNodo() );

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
    
    protected boolean listaVazia() {
        return (primeiraVar == null);
    }
    
}
