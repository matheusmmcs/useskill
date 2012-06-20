/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import br.ufpi.models.Alternativa;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class RespostaControllerTest {
    
    public RespostaControllerTest() {
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
     * Test of salvarRespostaEscrita method, of class RespostaController.
     */
    @Test
    public void testSalvarRespostaEscrita() {
        System.out.println("salvarRespostaEscrita");
        String resposta = "";
        RespostaController instance = null;
        instance.salvarRespostaEscrita(resposta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarRespostaAlternativa method, of class RespostaController.
     */
    @Test
    public void testSalvarRespostaAlternativa() {
        System.out.println("salvarRespostaAlternativa");
        Alternativa alternativa = null;
        RespostaController instance = null;
        instance.salvarRespostaAlternativa(alternativa);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exibir method, of class RespostaController.
     */
    @Test
    public void testExibir() {
        System.out.println("exibir");
        RespostaController instance = null;
        instance.exibir();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exibirRespostas method, of class RespostaController.
     */
    @Test
    public void testExibirRespostas() {
        System.out.println("exibirRespostas");
        Long testeId = null;
        Long perguntaId = null;
        RespostaController instance = null;
        instance.exibirRespostas(testeId, perguntaId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
