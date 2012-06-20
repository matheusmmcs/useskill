/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import br.ufpi.models.Pergunta;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class PerguntaControllerTest {
    
    public PerguntaControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of criarPergunta method, of class PerguntaController.
     */
    @Test
    public void testCriarPergunta() {
        System.out.println("criarPergunta");
        Long testeId = null;
        PerguntaController instance = null;
        Pergunta expResult = null;
        Pergunta result = instance.criarPergunta(testeId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editarPergunta method, of class PerguntaController.
     */
    @Test
    public void testEditarPergunta() {
        System.out.println("editarPergunta");
        Long testeId = null;
        Pergunta pergunta = null;
        PerguntaController instance = null;
        Pergunta expResult = null;
        Pergunta result = instance.editarPergunta(testeId, pergunta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarPergunta method, of class PerguntaController.
     */
    @Test
    public void testSalvarPergunta() {
        System.out.println("salvarPergunta");
        Long testeId = null;
        Pergunta pergunta = null;
        PerguntaController instance = null;
        instance.salvarPergunta(testeId, pergunta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of atualizarPergunta method, of class PerguntaController.
     */
    @Test
    public void testAtualizarPergunta() {
        System.out.println("atualizarPergunta");
        Long testeId = null;
        Pergunta pergunta = null;
        PerguntaController instance = null;
        instance.atualizarPergunta(testeId, pergunta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletarPergunta method, of class PerguntaController.
     */
    @Test
    public void testDeletarPergunta() {
        System.out.println("deletarPergunta");
        Long testeId = null;
        Long perguntaId = null;
        PerguntaController instance = null;
        instance.deletarPergunta(testeId, perguntaId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
