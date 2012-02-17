package br.ufpi.repositories;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufpi.util.HibernateUtil;

@RunWith(Suite.class)
@SuiteClasses({ TesteRepositoryImplTest.class, UsuarioRepositoryImplTest.class })
public class AllTests {

	@BeforeClass
	public static void limparBanco() {
		HibernateUtil.criarBanco();
	}

}
