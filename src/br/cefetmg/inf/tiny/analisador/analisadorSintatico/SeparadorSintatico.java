package br.cefetmg.inf.tiny.analisador.analisadorSintatico;

public class SeparadorSintatico {

    private String codigoCompleto;
    private String[] partesCodigo;

    private static int contadorTermos;

    public SeparadorSintatico(String codigoCompleto) {
        this.codigoCompleto = codigoCompleto;
        
        separa();
    }

    private void separa() {
        String[] temp;

        codigoCompleto = codigoCompleto.replaceAll("[(]", "([");
        codigoCompleto = codigoCompleto.replaceAll("[)]", "])");

        String regex = "\\s|\\t|[(]|[)]";
        temp = codigoCompleto.split(regex);

        partesCodigo = new String[temp.length];

        int i = 0;
        for (String str : temp) {
            if ((!str.equals("")) && (!str.equals("\\s"))) {
                partesCodigo[i] = str;
                i++;
            }
        }

        if (i < temp.length) {
            partesCodigo[i] = "flag";
        }

        int j = 0;
        for (String str : partesCodigo) {
            if (str != null) {
                if (str.contains("[")) {
                    partesCodigo[j] = partesCodigo[j].replace("[", "(");
                }
                if (str.contains("]")) {
                    partesCodigo[j] = partesCodigo[j].replace("]", ")");
                }
                if (j + 1 <= partesCodigo.length) {
                    j++;
                }
            }
        }

    }

    public String retornaProxTermo() {
        if ((contadorTermos + 1) <= partesCodigo.length) {
            String retorno = partesCodigo[contadorTermos];
            contadorTermos++;
            return retorno;
        }

        return "flag";
    }

    public void voltaUmTermo() {
        contadorTermos--;
    }

    public void imprimeTermos() {
        for (String str : partesCodigo) {
            System.out.println(str);
        }
    }

}
