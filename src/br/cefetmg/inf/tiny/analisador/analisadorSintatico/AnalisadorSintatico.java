package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

import br.cefetmg.inf.tiny.analisador.Analisador;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.util.DicionarioComandos;

public class AnalisadorSintatico extends Analisador {
    private String termo;
    private SeparadorSintatico termosCodigo;
    private boolean temComandoBloco;
    
    AnalisadorSintatico (String codigo) {
        termosCodigo = new SeparadorSintatico(codigo);
        filaExecucao = new Fila();
        pilhaComandos = new Pilha();
        
        temComandoBloco = false;
        
        analisa();
    }
    
    private void analisa () {
        try {
            termo = termosCodigo.retornaProxTermo();
            if (DicionarioComandos.verificaSeComando(termo)) {
                filaExecucao.insereFila(termo);
                switch (DicionarioComandos.qualComando(termo)) {
                    // print
                    case 0:
                        analisaPrint();
                        break;
                    // println
                    case 1:
                        analisaPrint();
                        break;
                    //"readInt",    2
                    case 2:
                        analisaReadInt();
                        break;
                    //"if",         3
                    case 3:
                        temComandoBloco = true;
                        analisaIf();
                        break;
                    //"then",       4
    //                case 4:
    //                    break;
                    //"else",       5
                    case 5:
                        analisaElse();
                        break;
                    //"endif",      6
                    case 6:
                        analisaEndif();
                        break;
                    //"while",      7
                    case 7:
                        temComandoBloco = true;
                        analisaWhile();
                        break;
                    //"do",         8
    //                case 8:
    //                    break;
                    //"endwhile",   9
                    case 9:
                        analisaEndwhile();
                        break;
                    //"for",        10
                    case 10:
                        temComandoBloco = true;
                        analisaFor();
                        break;
                    //"to",         11
    //                case 11:
    //                    break;
                    //"downto",     12
    //                case 12:
    //                    break;
                    //"endfor"      13
                    case 13:
                        analisaEndfor();
                        break;
                    // end
                    case 14:
                        analisaEnd();
                        break;
                }
            } else {
                char [] caracteresTermo = termo.toCharArray();
                if (!verificaSeAlfaMin(caracteresTermo[0])) {
                    throw new ExcecaoErroSintatico("Caractere não permitido no início de um comando");
                } else {
                    termosCodigo.voltaUmTermo();
                    analisaAtribuicao();
                }
            }
        } catch (ExcecaoErroSintatico erro) {
            System.err.println(erro.getMessage());
        } catch (ExcecaoPilhaVazia erro) {
            System.err.println(erro.getMessage());
        } catch (ExcecaoFilaVazia erro) {
            System.err.println(erro.getMessage());
        }
    }

    //
    //  INÍCIO MÉTODO DE ANALISE DOS COMANDOS PRINT E PRINTLN
    //
    private void analisaPrint() throws ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        boolean analisouParam = false;
        String expressaoImprimir = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char [] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando de impressão");
        }
        int i = 0;
        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            // nao encontrou outro comando nem terminou os termos do codigo
            analisouParam = true;
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if  (c == '(') {
                        // ele deve ser parênteses
                        pilhaParenteses.empilha("(");
                    } else {
                        throw new ExcecaoErroSintatico("O primeiro caractere após o comando de impressão não é '('");
                    }
                } else if (c == '(') {
                    // não é o primeiro caractere, não tem restrição
                    pilhaParenteses.empilha("(");
                } else if (c == ')') {
                    pilhaParenteses.desempilha();
                }
                i++;
            }
            expressaoImprimir += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando de impressão foram fechados");
        }
        filaExecucao.insereFila(expressaoImprimir);
        if (analisouParam) {
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros no comando de impressão");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DOS COMANDOS PRINT E PRINTLN
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO READINT
    //
    private void analisaReadInt() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        // expressão que conterá os parênteses e o nome da variável
        boolean analisouParam = false;
        String expressao = "";
        String proxTermo = termosCodigo.retornaProxTermo();
        boolean leuPrimeiroCharVar = false;
        char [] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        int contadorTermosAnalisados = -1;
        int i = 0;
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("O programa não termina como o esperado");
        }
        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            // enquanto não achar outro termo e o código não houver terminado
            analisouParam = true;
            contadorTermosAnalisados++;
            i = 0;
            caracteresTermo = proxTermo.toCharArray();
            for (char c : caracteresTermo) {
                if ((contadorTermosAnalisados == 0) && (i == 0)) {
                // primeiro termo, primeiro caractere
                    if (c == '(') {
                        pilhaParenteses.empilha("(");
                    } else {
                        throw new ExcecaoErroSintatico("O primeiro caractere depois do comando readInt não é um '('");     
                    }
                } else if ((contadorTermosAnalisados == 0) && (i == 1)) {
                // primeiro termo, segundo caractere
                    if (verificaSeAlfaMin(c)) {
                        leuPrimeiroCharVar = true;                    
                    } else {
                        throw new ExcecaoErroSintatico("Nome da variável no comando readInt é inválido");     
                    }
                } else if ((contadorTermosAnalisados == 1) && (i == 0)) {
                // segundo termo, primeiro caractere
                    if (leuPrimeiroCharVar == false) {
                    // se ainda não houver lido o primeiro caractere do nome da variável
                        if (verificaSeAlfaMin(c)) {
                            leuPrimeiroCharVar = true;                    
                        } else {
                            throw new ExcecaoErroSintatico("Nome da variável no comando readInt é inválido");     
                        }
                    }
                } else {
                    if ((contadorTermosAnalisados > 1) && (c != ')')) {
                        throw new ExcecaoErroSintatico("Nome da variável no comando readInt é inválido");     
                    }
                    if ((!verificaSeAlfaMin(c)) && (!verificaSeDigito(c))) {
                    // o caractere não é nem letra nem dígito
                        if (c == ')') {
                            pilhaParenteses.desempilha();
                        } else {
                            throw new ExcecaoErroSintatico("Nome da variável no comando readInt é inválido");     
                        }
                    }
                }
                i++;
            }
            expressao += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Os parênteses do comando readInt não foram fechados");
        }
        filaExecucao.insereFila(expressao);
        if (analisouParam) {
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros no readInt");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO READINT
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO IF
    //
    private void analisaIf() throws ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        boolean analisouParam = false;
        
        String expressaoCondicional = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char [] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando if");
        }
        int i;
        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if  (c == '(') {
                        // ele deve ser parênteses
                        pilhaParenteses.empilha("(");
                    } else {
                        throw new ExcecaoErroSintatico("O primeiro caractere após o comando if não é '('");
                    }
                } else if (c == '(') {
                    // não é o primeiro caractere, não tem restrição
                    pilhaParenteses.empilha("(");
                } else if (c == ')') {
                    pilhaParenteses.desempilha();
                }
                i++;
            }
            expressaoCondicional += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando if foram fechados");
        }
        if (analisouParam) {
            filaExecucao.insereFila(expressaoCondicional);
            if (proxTermo.equals("then")) {
                pilhaComandos.empilha("if");
                proxTermo = termosCodigo.retornaProxTermo();
                if (!proxTermo.equals("flag")) {
                    termosCodigo.voltaUmTermo();
                    analisa();
                } else {
                    throw new ExcecaoErroSintatico("O programa não termina como o esperado");
                }
            } else {
                throw new ExcecaoErroSintatico("O comando if não é seguido de then");
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros para o comando if");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO IF
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO ELSE
    //
    private void analisaElse() throws ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoFilaVazia {
        String comandoPilha = (String) pilhaComandos.desempilha();
        String proxTermo;
        
        if (comandoPilha.equals("if")) {
            pilhaComandos.empilha(comandoPilha);
            proxTermo = termosCodigo.retornaProxTermo();
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("O comando else se encontra no lugar errado");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO ELSE
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO ENDIF
    //
    private void analisaEndif() throws ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoFilaVazia {
        String comandoPilha = (String) pilhaComandos.desempilha();
        String proxTermo;
        
        if (comandoPilha.equals("if")) {
            proxTermo = termosCodigo.retornaProxTermo();
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("O comando endif se encontra no lugar errado");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO ENDIF
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO WHILE
    //
    private void analisaWhile() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        boolean analisouParam = false;
        
        String expressaoCondicional = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char [] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando while");
        }
        int i;
        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if  (c == '(') {
                        // ele deve ser parênteses
                        pilhaParenteses.empilha("(");
                    } else {
                        throw new ExcecaoErroSintatico("O primeiro caractere após o comando while não é '('");
                    }
                } else if (c == '(') {
                    // não é o primeiro caractere, não tem restrição
                    pilhaParenteses.empilha("(");
                } else if (c == ')') {
                    pilhaParenteses.desempilha();
                }
                i++;
            }
            expressaoCondicional += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando while foram fechados");
        }
        
        if (analisouParam) {
            filaExecucao.insereFila(expressaoCondicional);
            if (proxTermo.equals("do")) {
                filaExecucao.insereFila(proxTermo);
                pilhaComandos.empilha("while");
                proxTermo = termosCodigo.retornaProxTermo();
                if (!proxTermo.equals("flag")) {
                    termosCodigo.voltaUmTermo();
                    analisa();
                } else {
                    throw new ExcecaoErroSintatico("O programa não termina como o esperado");
                }
            } else {
                throw new ExcecaoErroSintatico("O comando while não é seguido de do");
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros para o comando while");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO WHILE
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO ENDWHILE
    //
    private void analisaEndwhile() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        String comandoPilha = (String) pilhaComandos.desempilha();
        String proxTermo;
        
        if (comandoPilha.equals("while")) {
            proxTermo = termosCodigo.retornaProxTermo();
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("O comando endwhile se encontra no lugar errado");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO ENDWHILE
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO FOR
    //
    private void analisaFor() throws ExcecaoErroSintatico, ExcecaoFilaVazia, ExcecaoPilhaVazia {
        boolean analisouParam = false;
        
        String expressaoAtribuicaoFor = "";
        char [] caracteresExp;

        String proxTermo = termosCodigo.retornaProxTermo();

        Pilha pilhaParenteses = new Pilha();

        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando for");
        }

        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo

            expressaoAtribuicaoFor += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }

        int cont;
        int posDoisPontos = -1;
        String nomeVar = "", atribuidor = "", valorVar = "";
        //boolean achouDoisPontos = false;
        if (analisouParam) {
            caracteresExp = expressaoAtribuicaoFor.toCharArray();
            cont = 0;
            for (char c : caracteresExp) {
                if ((cont == 0) && (!verificaSeAlfaMin(c))) {
                    throw new ExcecaoErroSintatico("Atribuição no comando for inválida");
                }
                if ((c == ':') && (nomeVar.equals(""))) {
                    nomeVar = expressaoAtribuicaoFor.substring(0, cont);
                    posDoisPontos = cont;
                }
                if ((posDoisPontos != -1) && (cont == posDoisPontos+1)) {
                    if (c == '=') {
                        atribuidor = ":=";
                        valorVar = expressaoAtribuicaoFor.substring(cont+1, expressaoAtribuicaoFor.length());
                    } else {
                        throw new ExcecaoErroSintatico("Atribuição no comando for inválida: caractere antes do =");
                    }
                }
                cont++;
            }
        } else {
            throw new ExcecaoErroSintatico ("Não há atribuição depois do for");
        }
        
        if (posDoisPontos == -1)
            throw new ExcecaoErroSintatico("Não há := na atribuição");
        
        for (char c : nomeVar.toCharArray()) {
            if (!verificaSeAlfaMin(c) && !verificaSeDigito(c))
                throw new ExcecaoErroSintatico("Atribuição no comando for inválida");
        }
        
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando while foram fechados");
        }

        filaExecucao.insereFila(nomeVar);
        filaExecucao.insereFila(atribuidor);
        filaExecucao.insereFila(valorVar);
        if (proxTermo.equals("downto") || proxTermo.equals("to")) {
            filaExecucao.insereFila(proxTermo);
            proxTermo = termosCodigo.retornaProxTermo();
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("O comando for não é seguido de to ou downto");
        }

        analisouParam = false;
        String expressao = "";
        proxTermo = termosCodigo.retornaProxTermo();
        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo
            expressao += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }

        if (analisouParam) {
            filaExecucao.insereFila(expressao);
            if (proxTermo.equals("do")) {
                filaExecucao.insereFila(proxTermo);
                pilhaComandos.empilha("for");
                proxTermo = termosCodigo.retornaProxTermo();
                if (!proxTermo.equals("flag")) {
                    termosCodigo.voltaUmTermo();
                    analisa();
                } else {
                    throw new ExcecaoErroSintatico("O programa não termina como o esperado");
                }
            } else {
                throw new ExcecaoErroSintatico("O comando for não é seguido de do");
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros para o comando for");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO FOR
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO ENDFOR
    //
    private void analisaEndfor() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoErroSintatico {
        String comandoPilha = (String) pilhaComandos.desempilha();
        String proxTermo;
        
        if (comandoPilha.equals("for")) {
            proxTermo = termosCodigo.retornaProxTermo();
            if (!proxTermo.equals("flag")) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            }
        } else {
            throw new ExcecaoErroSintatico("O comando endfor se encontra no lugar errado");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO ENDFOR
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO DE ATRIBUIÇÃO
    //
    private void analisaAtribuicao() throws ExcecaoFilaVazia, ExcecaoErroSintatico {
        String expressaoAtribuicaoFor = "";
        char [] caracteresExp;

        String proxTermo = termosCodigo.retornaProxTermo();

        Pilha pilhaParenteses = new Pilha();

        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando de atribuição");
        }

        while (!DicionarioComandos.verificaSeComando(proxTermo) && !proxTermo.equals("flag")) {
            // nao encontrou outro comando nem terminou os termos do codigo
            expressaoAtribuicaoFor += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }

        int cont = 0;
        int posDoisPontos = -1;
        String nomeVar = "", atribuidor = "", valorVar = "";
        caracteresExp = expressaoAtribuicaoFor.toCharArray();

        for (char c : caracteresExp) {
            if ((cont == 0) && (!verificaSeAlfaMin(c))) {
                throw new ExcecaoErroSintatico("Atribuição inválida");
            }
            if ((c == ':') && (nomeVar.equals(""))) {
                nomeVar = expressaoAtribuicaoFor.substring(0, cont);
                posDoisPontos = cont;
            }
            if ((posDoisPontos != -1) && (cont == posDoisPontos+1)) {
                if (c == '=') {
                    atribuidor = ":=";
                    valorVar = expressaoAtribuicaoFor.substring(cont+1, expressaoAtribuicaoFor.length());
                } else {
                    throw new ExcecaoErroSintatico("Atribuição inválida: caractere antes do =");
                }
            }
            cont++;
        }
        
//        if (posDoisPontos == -1)
//            throw new ExcecaoErroSintatico("Não há := na atribuição");
        
        for (char c : nomeVar.toCharArray()) {
            if (!verificaSeAlfaMin(c) && !verificaSeDigito(c))
                throw new ExcecaoErroSintatico("Atribuição inválida");
        }
        
        if (!pilhaParenteses.estaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando de atribuição foram fechados");
        }

        filaExecucao.insereFila(nomeVar);
        filaExecucao.insereFila(atribuidor);
        filaExecucao.insereFila(valorVar);
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("O programa não termina como o esperado");
        } else {
            termosCodigo.voltaUmTermo();
            analisa();
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO DE ATRIBUIÇÃO
    //
    
    //
    // INÍCIO MÉTODO DE ANÁLISE DO COMANDO DE ENCERRAMENTO DO PROGRAMA
    //
    private void analisaEnd () throws ExcecaoErroSintatico, ExcecaoFilaVazia, ExcecaoPilhaVazia {
        if (temComandoBloco) {
            if (!pilhaComandos.estaVazia()) {
                throw new ExcecaoErroSintatico ("O end veio antes do esperado.");
            }
        }
        filaExecucao.imprimeFila();
    }
    //
    // FIM MÉTODO DE ANÁLISE DO COMANDO DE ENCERRAMENTO DO PROGRAMA
    //


    //
    //  MÉTODOS DE VERIFICAÇÃO DO TIPO DO CARACTERE
    //
    private boolean verificaSeAlfaMin(char c) {
        return (((int)c >= 97) && ((int)c <= 122));
    }
    private boolean verificaSeDigito (char c) {
        return (((int)c >= 48) && ((int)c <= 57));
    }
}
