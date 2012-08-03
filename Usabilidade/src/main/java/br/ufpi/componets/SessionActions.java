 package br.ufpi.componets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Action;

/**
 * Usado para gravaÃ§Ã£o do fluxo. Quando terminar a gravaÃ§Ã£o do fluxo deleta a
 * seÃ§Ã£o.
 * 
 * @author Cleiton
 * 
 */
@SessionScoped
@Component
public class SessionActions {
	/**
	 * Lista de aÃ§Ãµes de um determinado FluxoComponente
	 */
	private List<Action> acoes;
	/**
	 * False se nao for a primeira vez que a pagina foi aberta True se for a
	 * primeira vez.
	 */
	private boolean primeiraPagina;

	/**
	 * Url que o browser vai estar
	 */
	//private String urlProxima;

//	public String getUrlproxima() {
//		return urlProxima;
//	}
//
//	public void setUrlProxima(String urlEmAndamento) {
//		this.urlProxima = urlEmAndamento;
//	}

	@PreDestroy
	public void destroy() {
		acoes = null;
	}

	public List<Action> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Action> acoes) {
		this.acoes = acoes;
	}

	public SessionActions() {
		primeiraPagina = true;
	}

	public boolean addAction(List<Action> actions) {
		if (acoes == null)
			this.acoes = new ArrayList<Action>();
		return this.acoes.addAll(actions);
	}

        public boolean isPrimeiraPagina() {
		return primeiraPagina;
	}

        public void setPrimeiraPagina(boolean primeiraPagina) {
		this.primeiraPagina = primeiraPagina;
	}

}
