/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.util.calculadora;

import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import java.util.Scanner;

/**
 *
 * @author Nícolas
 */
public class NewMain {

    public static void main(String[] args) throws ExcecaoExpressaoInvalida, ExcecaoPilhaVazia {
       Scanner in = new Scanner(System.in);
       
       String entrada = in.nextLine();
       while(!entrada.equals("-1")) {
           System.out.println("Resultado: " + Calculadora.iniciaCalculadora(entrada) + "\n");
           entrada = in.nextLine();
       }
    }
    
}
