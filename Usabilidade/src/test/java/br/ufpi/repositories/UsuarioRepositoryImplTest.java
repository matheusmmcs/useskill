package br.ufpi.repositories;

import org.junit.Before;
import org.junit.Test;

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
	public void listProjectIfUserIsTheOwner() throws Exception {
		System.out.println(dao.isContainsEmail("cleitonmoura18@hotmail.com"));
	}

}
