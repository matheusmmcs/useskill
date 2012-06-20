/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class TesteControllerTest {
    
    public TesteControllerTest() {
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
     * Test of criarTeste method, of class TesteController.
     */
    @Test
    public void testCriarTeste() {
        System.out.println("criarTeste");
        TesteController instance = null;
        instance.criarTeste();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvar method, of class TesteController.
     */
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        String titulo = "";
        TesteController instance = null;
        instance.salvar(titulo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passo1 method, of class TesteController.
     */
    @Test
    public void testPasso1() {
        System.out.println("passo1");
        Long id = null;
        TesteController instance = null;
        instance.passo1(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passo2 method, of class TesteController.
     */
    @Test
    public void testPasso2_4args() {
        System.out.println("passo2");
        Long idTeste = null;
        String titulo = "";
        String tituloPublico = "";
        String textoIndroducao = "";
        TesteController instance = null;
        instance.passo2(idTeste, titulo, tituloPublico, textoIndroducao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passo2 method, of class TesteController.
     */
    @Test
    public void testPasso2_Long() {
        System.out.println("passo2");
        Long idTeste = null;
        TesteController instance = null;
        instance.passo2(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passo3 method, of class TesteController.
     */
    @Test
    public void testPasso3() {
        System.out.println("passo3");
        Long idTeste = null;
        TesteController instance = null;
        instance.passo3(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passo4 method, of class TesteController.
     */
    @Test
    public void testPasso4() {
        System.out.println("passo4");
        Long idTeste = null;
        TesteController instance = null;
        instance.passo4(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of liberarTeste method, of class TesteController.
     */
    @Test
    public void testLiberarTeste() {
        System.out.println("liberarTeste");
        Long idTeste = null;
        TesteController instance = null;
        instance.liberarTeste(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convidarUsuario method, of class TesteController.
     */
    @Test
    public void testConvidarUsuario() {
        System.out.println("convidarUsuario");
        List<Long> idUsuarios = null;
        Long idTeste = null;
        TesteController instance = null;
        instance.convidarUsuario(idUsuarios, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of desconvidarUsuario method, of class TesteController.
     */
    @Test
    public void testDesconvidarUsuario() {
        System.out.println("desconvidarUsuario");
        List<Long> idUsuarios = null;
        Long idTeste = null;
        TesteController instance = null;
        instance.desconvidarUsuario(idUsuarios, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class TesteController.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Long idTeste = null;
        TesteController instance = null;
        instance.remove(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removed method, of class TesteController.
     */
    @Test
    public void testRemoved() {
        System.out.println("removed");
        String senha = "";
        Long idTeste = null;
        TesteController instance = null;
        instance.removed(senha, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convidar method, of class TesteController.
     */
    @Test
    public void testConvidar() {
        System.out.println("convidar");
        Long idTeste = null;
        TesteController instance = null;
        instance.convidar(idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of realizarTeste method, of class TesteController.
     */
    @Test
    public void testRealizarTeste() {
        System.out.println("realizarTeste");
        TesteController instance = null;
        instance.realizarTeste();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of meusProjetos method, of class TesteController.
     */
    @Test
    public void testMeusProjetos() {
        System.out.println("meusProjetos");
        TesteController instance = null;
        instance.meusProjetos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exibir method, of class TesteController.
     */
    @Test
    public void testExibir() {
        System.out.println("exibir");
        Long idTeste = null;
        TesteController instance = null;
        StringBuilder expResult = null;
        StringBuilder result = instance.exibir(idTeste);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
