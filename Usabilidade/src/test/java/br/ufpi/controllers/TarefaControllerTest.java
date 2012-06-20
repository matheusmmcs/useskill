/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import br.ufpi.models.Tarefa;
import br.ufpi.util.TarefaDetalhe;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Cleiton
 */
public class TarefaControllerTest {
    
    public TarefaControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of criarTarefa method, of class TarefaController.
     */
    @Test
    public void testCriarTarefa() {
        System.out.println("criarTarefa");
        Long testeId = null;
        Tarefa tarefa = null;
        TarefaController instance = null;
        Tarefa expResult = null;
        Tarefa result = instance.criarTarefa(testeId, tarefa);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of salvarTarefa method, of class TarefaController.
     */
    @Test
    public void testSalvarTarefa() {
        System.out.println("salvarTarefa");
        Tarefa tarefa = null;
        Long idTeste = null;
        TarefaController instance = null;
        instance.salvarTarefa(tarefa, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editarTarefa method, of class TarefaController.
     */
    @Test
    public void testEditarTarefa() {
        System.out.println("editarTarefa");
        Long idTeste = null;
        Tarefa tarefa = null;
        boolean isErro = false;
        TarefaController instance = null;
        Tarefa expResult = null;
        Tarefa result = instance.editarTarefa(idTeste, tarefa, isErro);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTarefa method, of class TarefaController.
     */
    @Test
    public void testUpdateTarefa() {
        System.out.println("updateTarefa");
        Tarefa tarefa = null;
        Long idTeste = null;
        TarefaController instance = null;
        instance.updateTarefa(tarefa, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removed method, of class TarefaController.
     */
    @Test
    public void testRemoved() {
        System.out.println("removed");
        Long idTarefa = null;
        Long idTeste = null;
        TarefaController instance = null;
        instance.removed(idTarefa, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveFluxoIdeal method, of class TarefaController.
     */
    @Test
    public void testSaveFluxoIdeal() {
        System.out.println("saveFluxoIdeal");
        String dados = "";
        Boolean completo = null;
        TarefaController instance = null;
        instance.saveFluxoIdeal(dados, completo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveFluxoUsuario method, of class TarefaController.
     */
    @Test
    public void testSaveFluxoUsuario() {
        System.out.println("saveFluxoUsuario");
        String dados = "";
        Boolean completo = null;
        Long tarefaId = null;
        TarefaController instance = null;
        String expResult = "";
        String result = instance.saveFluxoUsuario(dados, completo, tarefaId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciarGravacao method, of class TarefaController.
     */
    @Test
    public void testIniciarGravacao() {
        System.out.println("iniciarGravacao");
        Long idTarefa = null;
        Long idTeste = null;
        TarefaController instance = null;
        instance.iniciarGravacao(idTarefa, idTeste);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadtasktester method, of class TarefaController.
     */
    @Test
    public void testLoadtasktester() {
        System.out.println("loadtasktester");
        TarefaController instance = null;
        TarefaDetalhe expResult = null;
        TarefaDetalhe result = instance.loadtasktester();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadactiontester method, of class TarefaController.
     */
    @Test
    public void testLoadactiontester() {
        System.out.println("loadactiontester");
        TarefaController instance = null;
        String expResult = "";
        String result = instance.loadactiontester();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadtaskuser method, of class TarefaController.
     */
    @Test
    public void testLoadtaskuser() {
        System.out.println("loadtaskuser");
        TarefaController instance = null;
        TarefaDetalhe expResult = null;
        TarefaDetalhe result = instance.loadtaskuser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadactionuser method, of class TarefaController.
     */
    @Test
    public void testLoadactionuser() {
        System.out.println("loadactionuser");
        TarefaController instance = null;
        String expResult = "";
        String result = instance.loadactionuser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
