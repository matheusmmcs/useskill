package br.ufpi.repositories;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import br.ufpi.controllers.procedure.UserTestProcedure;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.UsuarioRepositoryImpl;

public abstract class AbstractDaoTest {

	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	protected Mockery mockery;

	@BeforeClass
	public static void prepare() {
		HashMap<String, String> configuracao = new HashMap<String, String>();
		configuracao.put("hibernate.connection.url",
				"jdbc:mysql://localhost:3306/usabilidadeTest");
		entityManagerFactory = Persistence.createEntityManagerFactory(
				"default", configuracao);
		populator(entityManagerFactory.createEntityManager());
	}

	@AfterClass
	public static void destroy() {
		entityManagerFactory.close();
	}

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		entityManager.getTransaction().rollback();
		entityManager.clear();
		entityManager.close();

	}

	public static void populator(EntityManager entityManager) {
		UsuarioRepositoryImpl repositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		entityManager.getTransaction().begin();
		List<Usuario> find = repositoryImpl.findAll();
		if (find.size()==0) {
			repositoryImpl.create(UserTestProcedure.newInstaceUsuario(
					entityManager, "cleiton", "cleitonmoura18@gmail.com",
					"senha1"));
			repositoryImpl.create(UserTestProcedure.newInstaceUsuario(
					entityManager, "cleiton moura",
					"cleitonmoura18@hotmail.com", "senha2"));
			repositoryImpl.create(UserTestProcedure.newInstaceUsuario(
					entityManager, "clebert", "clebertmoura18@gmail.com",
					"senha1"));
			repositoryImpl.create(UserTestProcedure.newInstaceUsuario(
					entityManager, "claudia", "claudiamoura18@gmail.com",
					"senha1"));
			repositoryImpl.create(UserTestProcedure.newInstaceUsuario(
					entityManager, "Maria", "maria@gmail.com", "senha1"));
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

}