/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author Cleiton
 */
public class LoginControllerTest {
    
    public LoginControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of login method, of class LoginController.
     */
   

    /**
     * Test of conta method, of class LoginController.
     */
    @Test
    @Ignore
    public void testAutenticar() {
        System.out.println("autenticar");
        String email = "";
        String senha = "";
        LoginController instance = null;
        instance.conta(email, senha);
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class LoginController.
     */
    @Test
    @Ignore
    public void testLogout() {
        System.out.println("logout");
        LoginController instance = null;
        instance.logout();
        fail("The test case is a prototype.");
    }

    /**
     * Test of validarInscricao method, of class LoginController.
     */
    @Test
    @Ignore
    public void testValidarInscricao() {
        System.out.println("validarInscricao");
        String confirmacaoEmail = "";
        LoginController instance = null;
        instance.validarInscricao(confirmacaoEmail);
        fail("The test case is a prototype.");
    }
}
