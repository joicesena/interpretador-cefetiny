package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;

public final class OrganizadorDePilha {

    public static Pilha organizarExpressao(String expressaoCompleta) throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida {
        int i = 0;
        double numeroCompleto;
        Object[] elementosExpressao = expressaoCompleta.split("");
        Pilha pilhaOrganizada = new Pilha();
        String concatenaNumero = "",
               elemento,
               elementoAnterior = null;

        do {
            elemento = (String) elementosExpressao[i];

            VerificadorDeIncoerencias.verificaSequenciaInvalida(elemento, elementoAnterior);
            
            //ElementoAtual = Número
            if (elemento.equals("0") || elemento.equals("1")
                    || elemento.equals("2") || elemento.equals("3")
                    || elemento.equals("4") || elemento.equals("5")
                    || elemento.equals("6") || elemento.equals("7")
                    || elemento.equals("8") || elemento.equals("9")) {
                concatenaNumero += elementosExpressao[i];
            } else {
                if (elemento.equals("d")) {
                    if (elementosExpressao[i + 1].equals("i") && elementosExpressao[i + 2].equals("v")) {
                        i += 2;
                        elementosExpressao[i] = "d";
                    } else {
                        throw new ExcecaoExpressaoInvalida();
                    }
                }
                if (!concatenaNumero.equals("")) {
                    numeroCompleto = Double.parseDouble(concatenaNumero);
                    pilhaOrganizada.empilha(numeroCompleto);
                    concatenaNumero = "";
                }
                pilhaOrganizada.empilha(elementosExpressao[i]);
            }
            i++;

            elementoAnterior = elemento;

        } while (i != elementosExpressao.length);
        pilhaOrganizada.empilha(concatenaNumero);

        return pilhaOrganizada;
    }
}
