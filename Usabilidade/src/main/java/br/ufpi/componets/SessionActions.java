package br.ufpi.componets;

import java.util.List;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Acao;
import br.ufpi.models.Tarefa;

/**
 * Usado para gravação do fluxo. Quando terminar a gravação do fluxo deleta a
 * seção.
 * 
 * @author Cleiton
 * 
 */
@SessionScoped
@Component
public class SessionActions {
	/**
	 * Lista de ações de um determinado Fluxo
	 */
	private List<Acao> acoes;
	/**
	 * Tarefa ao qual um determinado Fluxo vai pertencer
	 */
	private Tarefa tarefa;

	@PreDestroy
	public void destroy() {
		System.out
				.println("###########################Deletado Secao###############");
		acoes = null;
		tarefa = null;
	}

	public List<Acao> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Acao> acoes) {
		this.acoes = acoes;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public SessionActions() {
		System.out.println("####################3Contruindo##############");
	}

	public boolean addAction(List<Acao> actions) {
		return this.acoes.addAll(actions);
	}

}
