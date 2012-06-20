/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import br.ufpi.repositories.AbstractDaoTest;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class LoginControllerTest extends AbstractDaoTest{
       /**
     * Test of login method, of class LoginController.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String email = "";
        LoginController instance = null;
        instance.login(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of conta method, of class LoginController.
     */
    @Test
    public void testConta() {
        System.out.println("conta");
        String email = "";
        String senha = "";
        LoginController instance = null;
        instance.conta(email, senha);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logado method, of class LoginController.
     */
    @Test
    public void testLogado() {
        System.out.println("logado");
        LoginController instance = null;
        instance.logado();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class LoginController.
     */
    @Test
    public void testLogout() {
        System.out.println("logout");
        LoginController instance = null;
        instance.logout();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validarInscricao method, of class LoginController.
     */
    @Test
    public void testValidarInscricao() {
        System.out.println("validarInscricao");
        String confirmacaoEmail = "";
        LoginController instance = null;
        instance.validarInscricao(confirmacaoEmail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recuperaSenha method, of class LoginController.
     */
    @Test
    public void testRecuperaSenha() {
        System.out.println("recuperaSenha");
        String email = "";
        LoginController instance = null;
        instance.recuperaSenha(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recuperaSenhaCompleta method, of class LoginController.
     */
    @Test
    public void testRecuperaSenhaCompleta() {
        System.out.println("recuperaSenhaCompleta");
        String email = "";
        LoginController instance = null;
        String expResult = "";
        String result = instance.recuperaSenhaCompleta(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reenviaEmailConfirmacao method, of class LoginController.
     */
    @Test
    public void testReenviaEmailConfirmacao() {
        System.out.println("reenviaEmailConfirmacao");
        String email = "";
        LoginController instance = null;
        instance.reenviaEmailConfirmacao(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reenviaEmailConfirmacaoCompleto method, of class LoginController.
     */
    @Test
    public void testReenviaEmailConfirmacaoCompleto() {
        System.out.println("reenviaEmailConfirmacaoCompleto");
        String email = "";
        LoginController instance = null;
        instance.reenviaEmailConfirmacaoCompleto(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
