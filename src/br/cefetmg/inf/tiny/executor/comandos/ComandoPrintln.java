package br.cefetmg.inf.tiny.executor.comandos;

import br.cefetmg.inf.calculadora.Calculadora;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.util.Dicionarios;

public final class ComandoPrintln extends Comando {

    private int tipoImpressao;

    public ComandoPrintln(String parametro) throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        super(parametro);
        
        this.analisa();
    }

    @Override
    public void executaComando() throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
        if (tipoImpressao == 1) {
            System.out.println();
        } else if (tipoImpressao == 2) {
            System.out.println(Calculadora.iniciaCalculadora(parametro));
        } else {
            System.out.println(parametro);
        }
    }

    @Override
    public void analisa() throws ExcecaoPilhaVazia, ExcecaoFilaVazia, ExcecaoExpressaoInvalida {
        parametro = parametro.substring(1, (parametro.length() - 1));
        parametro = parametro.replace(" ", "");

        if (parametro.startsWith("\"") && parametro.endsWith("\"") && parametro.length() == 2) {
            tipoImpressao = 1;
        } else if (parametro.length() == 1) {
            Calculadora.formataAnalisaExpressao(parametro);
            tipoImpressao = 2;
        } else if (parametro.length() == 0) {
            tipoImpressao = 1;
        } else {
            int contaAspas = 0;
            int i = 0;

            String saidaFinal = "";
            String strAux = "";
            String[] vetParam = parametro.split("");
            //
            do {
                if (vetParam[i].equals("\"")) {
                    while (contaAspas != 2 && i < vetParam.length) {
                        if (vetParam[i].equals("\"")) {
                            contaAspas++;
                        }
                        strAux += vetParam[i];
                        i++;
                    }
                    if (contaAspas != 2) {
                        throw new ExcecaoExpressaoInvalida("Comando 'println':\n\tstring não foi finalizada");
                    } else {
                        Calculadora.formataAnalisaExpressao(strAux);
                        saidaFinal += strAux.substring(1, (strAux.length() - 1));
                        strAux = "";
                        contaAspas = 0;
                    }
                } else if (vetParam[i].equals("+")) {
                    i++;
                    while (i < vetParam.length && !vetParam[i].equals("\"")) {
                        strAux += vetParam[i];
                        i++;
                    }
                    saidaFinal += Calculadora.iniciaCalculadora(strAux);;
                    strAux = "";
                } else if (Dicionarios.procuraElementoNoDicionario(vetParam[i], Dicionarios.ALFABETO)
                           || Dicionarios.procuraElementoNoDicionario(vetParam[i], Dicionarios.INTEIROS)) {
                    while (i < vetParam.length && !vetParam[i].equals("\"")) {
                        strAux += vetParam[i];
                        i++;
                    }
                    saidaFinal += Calculadora.iniciaCalculadora(strAux);
                    strAux = "";
                } else {
                    throw new ExcecaoExpressaoInvalida("Comando 'println':\n\to parâmetro de impressão não é válido");
                }
                i++;
            } while (i < vetParam.length);
            
            parametro = saidaFinal;
            tipoImpressao = 3;
        }

        executaComando();
    }
}
