package br.ufpi.componets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;

@Component
@SessionScoped
public class SessionFluxoTarefa {
	List<Long> tarefas;

	public List<Long> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Long> tarefas) {
		this.tarefas = tarefas;
	}

	/**
	 * Criar uma lista com o identificador das tarefas a serem realizadas em um
	 * teste;
	 * 
	 * @param teste
	 */
	public void criarLista(Teste teste) {
		tarefas = new ArrayList<Long>();
		for (Tarefa tarefa : teste.getTarefas()) {
			this.tarefas.add(tarefa.getId());

		}
	}

	public Long getProximo() {
		if (tarefas.size() > 0) {
			tarefas.remove(0);
			return tarefas.get(0);
		} else
			return null;
	}

	@PreDestroy
	public void destroy() {
		this.tarefas = null;
	}

	/**
	 * Retorna a tarefa que esta sendo usada no momento
	 * 
	 * @return
	 */
	public Long getVez() {
		return this.getTarefas().get(0);
	}
}
