package br.ufpi.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufpi.repositories.UsuarioRepositoryImplTest;
import br.ufpi.util.CriptografiaTeste;

@RunWith(Suite.class)
@SuiteClasses({ LoginControllerTest.class, PerguntaControllerTest.class,
		RespostaControllerTest.class, TarefaControllerTest.class,
		TesteControllerTest.class, TesteParticiparControllerTest.class,
		UsuarioControllerTest.class,UsuarioRepositoryImplTest.class,CriptografiaTeste.class})
public class AllTests {

}
