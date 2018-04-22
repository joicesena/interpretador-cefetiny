package br.cefetmg.inf.comandos;

public abstract class Comando {
    private final String parametro;

    public Comando(String parametro) {
        this.parametro = parametro;
    }
    
    public abstract void executaComando();
}
