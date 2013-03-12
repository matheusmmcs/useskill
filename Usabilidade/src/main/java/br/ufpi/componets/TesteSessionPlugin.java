package br.ufpi.componets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.TipoConvidado;

@SessionScoped
@Component
public class TesteSessionPlugin {
	private Long idTeste;
	private TipoConvidado tipoConvidado;
	private List<ObjetoSalvo> objetosSalvos;

	/**
	 * 
	 */
	public TesteSessionPlugin() {
		super();
	}

	/**
	 * @return the idTeste
	 */
	public Long getIdTeste() {
		return idTeste;
	}

	/**
	 * @param idTeste
	 *            the idTeste to set
	 */
	public void setIdTeste(Long idTeste) {
		this.idTeste = idTeste;
	}

	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

	/**
	 * @return the objetosSalvos
	 */
	public List<ObjetoSalvo> getObjetosSalvos() {
		return objetosSalvos;
	}

	/**
	 * @param objetosSalvos
	 *            the objetosSalvos to set
	 */
	public void setObjetosSalvos(List<ObjetoSalvo> objetosSalvos) {
		this.objetosSalvos = objetosSalvos;
	}

	public void addObjetosSalvos(ObjetoSalvo objetoSalvo) {
		if (objetosSalvos == null) {
			objetosSalvos = new ArrayList<ObjetoSalvo>();
		}
		objetosSalvos.add(objetoSalvo);
	}

	/**
	 * Método que ira remover todos os objetos de um teste não completo. Ao
	 * remover ele instancia uma nova lista de objetos
	 */
	@PreDestroy
	public void removerObjetosSalvos() {
		// TODO o que esta em testeParticipar trazer para ca
	}

	/**
	 * 
	 */
	public void termino() {
		this.idTeste = null;
		this.objetosSalvos = null;
		this.tipoConvidado = null;
	}
}
