/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class TesteParticiparControllerTest {
    
    public TesteParticiparControllerTest() {
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
     * Test of negar method, of class TesteParticiparController.
     */
    @Test
    public void testNegar() {
        System.out.println("negar");
        Long testeId = null;
        TesteParticiparController instance = null;
        instance.negar(testeId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aceitar method, of class TesteParticiparController.
     */
    @Test
    public void testAceitar() {
        System.out.println("aceitar");
        Long testeId = null;
        TesteParticiparController instance = null;
        instance.aceitar(testeId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of termino method, of class TesteParticiparController.
     */
    @Test
    public void testTermino() {
        System.out.println("termino");
        TesteParticiparController instance = null;
        instance.termino();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of responder method, of class TesteParticiparController.
     */
    @Test
    public void testResponder() {
        System.out.println("responder");
        TesteParticiparController instance = null;
        instance.responder();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
