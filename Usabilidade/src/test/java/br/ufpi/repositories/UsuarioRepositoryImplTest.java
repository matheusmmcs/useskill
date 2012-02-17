/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ufpi.models.Usuario;
import br.ufpi.util.HibernateUtil;
import org.junit.Ignore;

/**
 *
 * @author Cleiton
 */
public class UsuarioRepositoryImplTest {

    Session session;
    UsuarioRepositoryImpl repository;
    Usuario usuario;

    public UsuarioRepositoryImplTest() {
       session = HibernateUtil.getSessionFactory().openSession();
        repository = new UsuarioRepositoryImpl(session);
        usuario = new Usuario("Cleiton", "cleitonmoura@hotmail.com", "senha",
                false, "confirmacao");
        tesCriarUsuario();
    }

    /**
     * Test of logar method, of class UsuarioRepositoryImpl.
     */
    @Test
    public void tesCriarUsuario() {

        System.out.println("Criar Usuario");
        Transaction transaction = session.beginTransaction();
        repository.create(usuario);
        transaction.commit();
        Assert.assertNotNull(usuario);

    }

    @Ignore
    @Test
    public void testLogar() {
        System.out.println("logar");
        String email = "cleitonmoura@hotmail.com";
        String senha = "6eea7de494081c05437e3c1dd92cf8bd";
        Usuario result = repository.logar(email, senha);
        Assert.assertNotNull(result);
        testFindConfirmacaoEmail();
    }

    /**
     * Test of findConfirmacaoEmail method, of class UsuarioRepositoryImpl.
     */
    public void testFindConfirmacaoEmail() {
        System.out.println("findConfirmacaoEmail");
        String confirmacaoEmail = "confirmacao";
        Usuario result = repository.findConfirmacaoEmail(confirmacaoEmail);
        Assert.assertNotNull(result);
    }
}
