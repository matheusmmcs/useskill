/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import br.ufpi.controllers.procedure.RespostaTestProcedure;
import br.ufpi.models.Alternativa;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;

/**
 *
 * @author Cleiton
 */
public class RespostaControllerTest extends AbstractDaoTest{
    

	private RespostaAlternativaRepository repository;
	private RespostaEscritaRepository escritaRepositoryImpl;
	private RespostaController instance;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = RespostaTestProcedure.newInstanceRespostaAlternativaRepository(entityManager);
		escritaRepositoryImpl=RespostaTestProcedure.newInstanceRespostaEscritaRepositoryImpl(entityManager);
		instance = RespostaTestProcedure.newInstanceTarefaController(entityManager, result);
	}
    /**
     * Test of salvarRespostaEscrita method, of class RespostaController.
     */
    @Test
    public void testSalvarRespostaEscrita() {
        System.out.println("salvarRespostaEscrita");
        String resposta = "";
        RespostaController instance = null;
        instance.salvarRespostaEscrita(resposta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarRespostaAlternativa method, of class RespostaController.
     */
    @Test
    public void testSalvarRespostaAlternativa() {
        System.out.println("salvarRespostaAlternativa");
        Alternativa alternativa = null;
        RespostaController instance = null;
        instance.salvarRespostaAlternativa(alternativa);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exibir method, of class RespostaController.
     */
    @Test
    public void testExibir() {
        System.out.println("exibir");
        RespostaController instance = null;
        instance.exibir();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exibirRespostas method, of class RespostaController.
     */
    @Test
    public void testExibirRespostas() {
        System.out.println("exibirRespostas");
        Long testeId = null;
        Long perguntaId = null;
        RespostaController instance = null;
        instance.exibirRespostas(testeId, perguntaId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
