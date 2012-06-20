package br.ufpi.repositories;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.UsuarioRepositoryImpl;

public class UsuarioRepositoryImplTest extends AbstractDaoTest {

	private UsuarioRepository dao;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new UsuarioRepositoryImpl(entityManager);
		
	}

	@Test
	public void contemEmail() throws Exception {
		Assert.assertTrue(dao.isContainsEmail("cleitonmoura18@hotmail.com"))
		;
	}
	@Test
	public void naoContemEmail() throws Exception {
		Assert.assertFalse(dao.isContainsEmail("cleitonmoura18@gmail.com"))
		;
	}
}
