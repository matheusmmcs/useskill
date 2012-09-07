package br.ufpi.repositories;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.caelum.vraptor.util.test.MockResult;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.UsuarioRepositoryImpl;

public abstract class AbstractDaoTest {

	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	protected MockResult result;

	@BeforeClass
	public static void prepare() {
		HashMap<String, String> configuracao = new HashMap<String, String>();
//		configuracao.put("hibernate.connection.url",
//				"jdbc:mysql://localhost:3306/usabilidadeTest");
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
		result = new MockResult();
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
		if (find.size() == 0) {
			Populator.userPopulator(entityManager);
			Populator.validarIncricao(entityManager, repositoryImpl.find(4l)
					.getConfirmacaoEmail());
			Populator.testPopulator(entityManager);
			Populator.convidarUsuarios(entityManager);
			Populator.criarPergunta(entityManager, 2l);
			Populator.criarPergunta(entityManager, 4l);
			Populator.criarTarefa(entityManager);
			entityManager.getTransaction().commit();
			entityManager.close();
		}
	}

}