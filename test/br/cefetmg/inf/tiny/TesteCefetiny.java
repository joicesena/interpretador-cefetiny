package br.cefetmg.inf.tiny;

import br.cefetmg.inf.tiny.analisador.AnalisadorSintatico;
import br.cefetmg.inf.tiny.entradaCodigo.LeitorArquivo;
import br.cefetmg.inf.tiny.estruturasDados.Pilha;
import br.cefetmg.inf.tiny.excecoes.ExcecaoExpressaoInvalida;
import br.cefetmg.inf.tiny.excecoes.ExcecaoFilaVazia;
import br.cefetmg.inf.tiny.excecoes.ExcecaoPilhaVazia;
import br.cefetmg.inf.tiny.memoria.EstruturaMemoria;
import br.cefetmg.inf.tiny.memoria.Variavel;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TesteCefetiny {

    private static EstruturaMemoria variaveis;

    public TesteCefetiny() {
    }

    @Ignore
    @BeforeClass
    public static void BeforeClass() throws ExcecaoExpressaoInvalida {
    }

    @Ignore
    @AfterClass
    public static void tearDownClass() {
    }

    @Ignore
    @Before
    public void setUp() {
    }

    @Ignore
    @After
    public void tearDown() {
    }

    @Test
    public void testeCefetiny() throws ExcecaoPilhaVazia, ExcecaoExpressaoInvalida, FileNotFoundException, ExcecaoFilaVazia {
        LeitorArquivo.leArquivo("test/br/cefetmg/inf/tiny/arqTestes/teste1.txt");
        AnalisadorSintatico aS = AnalisadorSintatico.getInstancia(LeitorArquivo.getCodigo());
        assert true;
    }
}
