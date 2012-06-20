package br.ufpi.repositories;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.caelum.iogi.Instantiator;

public abstract class AbstractDaoTest {
	
	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	protected Mockery mockery;

	@BeforeClass
	public static void prepare() {
		HashMap<String, String> configuracao= new HashMap<String,String>();
		configuracao.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/usabilidadeTest");
		entityManagerFactory= Persistence.createEntityManagerFactory("default",configuracao);
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

}