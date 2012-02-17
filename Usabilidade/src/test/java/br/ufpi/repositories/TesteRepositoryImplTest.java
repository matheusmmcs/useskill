package br.ufpi.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.HibernateUtil;
import org.junit.*;

public class TesteRepositoryImplTest {

	Session session;
	UsuarioRepositoryImpl repository;
	Usuario usuario;
	TesteRepositoryImpl testeRepositoryImpl;
	Teste teste;

	public TesteRepositoryImplTest() {
		session = HibernateUtil.getSessionFactory().openSession();
		repository = new UsuarioRepositoryImpl(session);
		usuario = new Usuario("Cleiton2",
				"clafsasfdadfasdfadsa@hotmail.comafsdf3klklk", "senha4", false,
				"confirmacao4");
		Transaction beginTransaction = session.beginTransaction();
		repository.create(usuario);
		beginTransaction.commit();
		testeRepositoryImpl = new TesteRepositoryImpl(session);
		teste = new Teste();
		// findTestUsuario();
	}
@Ignore
	@Test
	public void testCreate() {
		System.out.println("testeCreate");
		teste.setUsuarioCriador(usuario);
		Transaction beginTransaction = session.beginTransaction();
		testeRepositoryImpl.create(teste);
		beginTransaction.commit();
		Assert.assertNotNull(teste.getId());
	}

	/**
	 * Analisa se esta apenas destruidos os testes sem apagar o usuario
	 */
	@Test
	@Ignore
	public void destroyTeste() {
		System.out.println("destroyteste");
		Transaction beginTransaction = session.beginTransaction();
		testeRepositoryImpl.destroy(teste);
		beginTransaction.commit();
		Assert.assertNotNull(repository
				.findEmail("clafsasfdadfasdfadsa@hotmail.comafsdf3klklk"));
	}

	@Ignore
	@Test
	public void findTestUsuario() {
		Assert.assertEquals(1, repository.findTestesParticipados(usuario)
				.size());
	}
}
