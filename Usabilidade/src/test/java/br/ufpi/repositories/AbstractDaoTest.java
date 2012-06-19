package br.ufpi.repositories;

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
		entityManagerFactory= Persistence.createEntityManagerFactory("default");
		entityManager=entityManagerFactory.createEntityManager();
		
	}

	@AfterClass
	public static void destroy() {
		entityManagerFactory.close();
	}

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		entityManager.getTransaction().rollback();
		entityManager.clear();
		entityManager.close();
	}

}