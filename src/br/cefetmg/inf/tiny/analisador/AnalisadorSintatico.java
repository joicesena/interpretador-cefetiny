package br.cefetmg.inf.tiny.analisador;

import br.cefetmg.inf.tiny.analisador.analisadorSemantico.AnalisadorSemantico;
import br.cefetmg.inf.tiny.estruturasDados.Fila;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoEntradaInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoErroSintatico;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoListaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.executor.Executor;
import br.cefetmg.inf.util.Dicionarios;

public final class AnalisadorSintatico extends Analisador {

    private static AnalisadorSintatico instancia;

    private static AnalisadorSemantico analisadorSemantico;

    private SeparadorSintatico termosCodigo;

    private String termo;

    private boolean temComandoBloco;

    private AnalisadorSintatico(String codigo) throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoEntradaInvalida, ExcecaoListaVazia {
        temComandoBloco = false;

        filaExecucao = new Fila();

        pilhaComandos = new Pilha();

        termosCodigo = new SeparadorSintatico(codigo);
        this.analisa();

        Executor.executaPrograma(filaExecucao);
    }

    public static synchronized AnalisadorSintatico getInstancia(String codigo) throws ExcecaoFilaVazia, ExcecaoExpressaoInvalida, ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoEntradaInvalida, ExcecaoListaVazia {
        if (instancia == null) {
            instancia = new AnalisadorSintatico(codigo);
        }
        return instancia;
    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoErroSintatico {
        termo = termosCodigo.retornaProxTermo();
        if (Dicionarios.procuraElementoNoDicionario(termo, Dicionarios.LISTA_COMANDOS)) {
            filaExecucao.insereFila(termo);
            switch (Dicionarios.qualComando(termo)) {
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
            char[] caracteresTermo = termo.toCharArray();
            if (!verificaSeAlfaMin(caracteresTermo[0])) {
                throw new ExcecaoErroSintatico("Caractere não permitido no início de um comando");
            } else {
                termosCodigo.voltaUmTermo();
                analisaAtribuicao();
            }
        }

    }

    //
    //  INÍCIO MÉTODO DE ANALISE DOS COMANDOS PRINT E PRINTLN
    //
    @Override
    protected void analisaPrint() throws ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        boolean analisouParam = false;
        String expressaoImprimir = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char[] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando de impressão");
        }
        int i = 0;
        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
            // nao encontrou outro comando nem terminou os termos do codigo
            analisouParam = true;
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if (c == '(') {
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

            if (!pilhaParenteses.pilhaVazia()) {
                proxTermo = termosCodigo.retornaProxTermo();
            } else {
                break;
            }
        }
        if (!pilhaParenteses.pilhaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando de impressão foram fechados");
        }
        filaExecucao.insereFila(expressaoImprimir);
        if (analisouParam) {
            if (Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS)) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else if (proxTermo.equals("flag")) {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            } else {
                analisa();
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
    @Override
    protected void analisaReadInt() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        // expressão que conterá os parênteses e o nome da variável
        boolean analisouParam = false;
        String expressao = "";
        String proxTermo = termosCodigo.retornaProxTermo();
        boolean leuPrimeiroCharVar = false;
        char[] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        int contadorTermosAnalisados = -1;
        int i = 0;
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("O programa não termina como o esperado");
        }
        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
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

            if (!pilhaParenteses.pilhaVazia()) {
                proxTermo = termosCodigo.retornaProxTermo();
            } else {
                break;
            }
        }
        if (!pilhaParenteses.pilhaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando readInt foram fechados");
        }
        filaExecucao.insereFila(expressao);
        if (analisouParam) {
            if (Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS)) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else if (proxTermo.equals("flag")) {
                throw new ExcecaoErroSintatico("O programa não termina como o esperado");
            } else {
                analisa();
            }
        } else {
            throw new ExcecaoErroSintatico("Não há parâmetros no comando readInt");
        }
    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO READINT
    //

    //
    //  INÍCIO MÉTODO DE ANALISE DO COMANDO IF
    //
    @Override
    protected void analisaIf() throws ExcecaoErroSintatico, ExcecaoPilhaVazia, ExcecaoFilaVazia {
        boolean analisouParam = false;

        String expressaoCondicional = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char[] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando if");
        }
        int i;
        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if (c == '(') {
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
        if (!pilhaParenteses.pilhaVazia()) {
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
    @Override
    protected void analisaElse() throws ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoFilaVazia {
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
    @Override
    protected void analisaEndif() throws ExcecaoPilhaVazia, ExcecaoErroSintatico, ExcecaoFilaVazia {
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
    @Override
    protected void analisaWhile() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        boolean analisouParam = false;

        String expressaoCondicional = "";
        int contadorAnalisados = -1;
        String proxTermo = termosCodigo.retornaProxTermo();
        char[] caracteresTermo;
        Pilha pilhaParenteses = new Pilha();
        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando while");
        }
        int i;
        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo
            contadorAnalisados++;
            caracteresTermo = proxTermo.toCharArray();
            i = 0;
            for (char c : caracteresTermo) {
                if ((contadorAnalisados == 0) && (i == 0)) {
                    // primeiro termo depois do comando
                    // primeiro caractere do primeiro termo
                    if (c == '(') {
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
        if (!pilhaParenteses.pilhaVazia()) {
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
    @Override
    protected void analisaEndwhile() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
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
    @Override
    protected void analisaFor() throws ExcecaoErroSintatico, ExcecaoFilaVazia, ExcecaoPilhaVazia {
        boolean analisouParam = false;

        String expressaoAtribuicaoFor = "";
        char[] caracteresExp;

        String proxTermo = termosCodigo.retornaProxTermo();

        Pilha pilhaParenteses = new Pilha();

        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("Não há nada depois do comando for");
        }

        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
            analisouParam = true;
            // nao encontrou outro comando nem terminou os termos do codigo

            expressaoAtribuicaoFor += proxTermo;
            proxTermo = termosCodigo.retornaProxTermo();
        }

        int cont;
        int posDoisPontos = -1;
        String nomeVar = "", atribuidor = "", valorVar = "";
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
                if ((posDoisPontos != -1) && (cont == posDoisPontos + 1)) {
                    if (c == '=') {
                        atribuidor = ":=";
                        valorVar = expressaoAtribuicaoFor.substring(cont + 1, expressaoAtribuicaoFor.length());
                    } else {
                        throw new ExcecaoErroSintatico("Atribuição no comando for inválida: caractere antes do =");
                    }
                }
                cont++;
            }
        } else {
            throw new ExcecaoErroSintatico("Não há atribuição depois do for");
        }

        if (posDoisPontos == -1) {
            throw new ExcecaoErroSintatico("Não há := na atribuição");
        }

        for (char c : nomeVar.toCharArray()) {
            if (!verificaSeAlfaMin(c) && !verificaSeDigito(c)) {
                throw new ExcecaoErroSintatico("Atribuição no comando for inválida");
            }
        }

        if (!pilhaParenteses.pilhaVazia()) {
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
        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
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
    @Override
    protected void analisaEndfor() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoErroSintatico {
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
    @Override
    protected void analisaAtribuicao() throws ExcecaoFilaVazia, ExcecaoErroSintatico, ExcecaoPilhaVazia {
        String expressaoAtribuicao = "";
        char[] caracteresExp;

        String proxTermo;
        proxTermo = termosCodigo.retornaProxTermo();

        boolean achouAtribuidor = false;
        boolean temMaisAtribuicao = false;

        while (!Dicionarios.procuraElementoNoDicionario(proxTermo, Dicionarios.LISTA_COMANDOS) && !proxTermo.equals("flag")) {
            // enquanto não achar comando ou nao tiver acabado os termos do código
            if (!achouAtribuidor) {
                // se não achou atribuidor, faz parte da expressaoAtribuicao
                expressaoAtribuicao += proxTermo;
                if (proxTermo.contains(":")) {
                    if ((proxTermo.charAt((proxTermo.indexOf(":")) + 1)) == '=') {
                        achouAtribuidor = true;
                    }
                }
                proxTermo = termosCodigo.retornaProxTermo();
            } else {
                // já achou atribuidor
                if (proxTermo.contains(":")) {
                    temMaisAtribuicao = true;
                    // se o termo tiver :
                    if (proxTermo.startsWith(":")) {
                        // se for a primeira coisa do termo, vai ficar como [var] [:=] [exp]
                        // volta para [var]
                        termosCodigo.voltaUmTermo();
                        //volta para antes de [var]
                        termosCodigo.voltaUmTermo();
                        break;
                    } else {
                        // não é o primeiro caractere do termo
                        // seria algo como [var[:=]]
                        // volta para o termo anterior
                        termosCodigo.voltaUmTermo();
                        break;
                    }
                } else {
                    String proxProxTermo = termosCodigo.retornaProxTermo();
                    if (proxProxTermo.contains(":")) {
                        temMaisAtribuicao = true;
                        // se o termo tiver :
                        if (proxProxTermo.startsWith(":")) {
                            // se for a primeira coisa do termo, vai ficar como [var] [:=] [exp]
                            // volta para [var]
                            termosCodigo.voltaUmTermo();
                            //volta para antes de [var]
                            termosCodigo.voltaUmTermo();
                            break;
                        } else {
                            // não é o primeiro caractere do termo
                            // seria algo como [var[:=]]
                            // volta para o termo anterior
                            expressaoAtribuicao += proxTermo;
                            termosCodigo.voltaUmTermo();
                            break;
                        }
                    } else {
                        expressaoAtribuicao += proxTermo;
                        if ((!Dicionarios.procuraElementoNoDicionario(proxProxTermo, Dicionarios.LISTA_COMANDOS) && !proxProxTermo.equals("flag"))) {
                            termosCodigo.voltaUmTermo();
                            proxTermo = termosCodigo.retornaProxTermo();
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        Pilha pilhaParenteses = new Pilha();
        int cont = 0;
        int posDoisPontos = -1;
        String nomeVar = "", atribuidor = "", valorVar = "";
        caracteresExp = expressaoAtribuicao.toCharArray();

        for (char c : caracteresExp) {
            if ((cont == 0) && (!verificaSeAlfaMin(c))) {
                throw new ExcecaoErroSintatico("Atribuição inválida");
            }
            if ((c == ':') && (nomeVar.equals(""))) {
                nomeVar = expressaoAtribuicao.substring(0, cont);
                posDoisPontos = cont;
            }
            if ((posDoisPontos != -1) && (cont == posDoisPontos + 1)) {
                if (c == '=') {
                    atribuidor = ":=";
                    valorVar = expressaoAtribuicao.substring(cont + 1, expressaoAtribuicao.length());
                } else {
                    throw new ExcecaoErroSintatico("Atribuição inválida: caractere antes do =");
                }
            }
            cont++;
        }

        for (char c : nomeVar.toCharArray()) {
            if (!verificaSeAlfaMin(c) && !verificaSeDigito(c)) {
                throw new ExcecaoErroSintatico("Atribuição inválida");
            }
        }

        if (!pilhaParenteses.pilhaVazia()) {
            throw new ExcecaoErroSintatico("Nem todos os parênteses no comando de atribuição foram fechados");
        }

        filaExecucao.insereFila(nomeVar);
        filaExecucao.insereFila(atribuidor);
        filaExecucao.insereFila(valorVar);

        if (proxTermo.equals("flag")) {
            throw new ExcecaoErroSintatico("O programa não termina como o esperado");
        } else {
            if (!temMaisAtribuicao) {
                termosCodigo.voltaUmTermo();
                analisa();
            } else {
                analisaAtribuicao();
            }
        }

    }
    //
    //  FIM MÉTODO DE ANALISE DO COMANDO DE ATRIBUIÇÃO
    //

    //
    // INÍCIO MÉTODO DE ANÁLISE DO COMANDO DE ENCERRAMENTO DO PROGRAMA
    //
    @Override
    protected void analisaEnd() throws ExcecaoErroSintatico, ExcecaoFilaVazia, ExcecaoPilhaVazia {
        if (temComandoBloco) {
            if (!pilhaComandos.pilhaVazia()) {
                throw new ExcecaoErroSintatico("O end veio antes do esperado.");
            }
        }
        //filaExecucao.imprimeFila();
    }
    //
    // FIM MÉTODO DE ANÁLISE DO COMANDO DE ENCERRAMENTO DO PROGRAMA
    //

    //
    //  MÉTODOS DE VERIFICAÇÃO DO TIPO DO CARACTERE
    //
    private boolean verificaSeAlfaMin(char c) {
        return (((int) c >= 97) && ((int) c <= 122));
    }

    private boolean verificaSeDigito(char c) {
        return (((int) c >= 48) && ((int) c <= 57));
    }
}
