package br.ufpi.models;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import br.ufpi.componets.SessionFluxoTarefa;

public class Lista {
	private SessionFluxoTarefa fluxoTarefa;

	@Test
	public void test() {
		fluxoTarefa = new SessionFluxoTarefa();
		ArrayList<Long> tarefas = new ArrayList<Long>();
		tarefas.add(8l);
		fluxoTarefa.setTarefas(tarefas);
		Long vez = fluxoTarefa.getVez();
		Assert.assertEquals(8, vez, 0.1);
		fluxoTarefa.getProximo();
		Assert.assertEquals(0, fluxoTarefa.getVez(), 0.1);
		fluxoTarefa.getProximo();
		Assert.assertEquals(0, fluxoTarefa.getVez(), 0.1);
		fluxoTarefa.getProximo();
		Assert.assertEquals(0, fluxoTarefa.getVez(), 0.1);

	}

}
