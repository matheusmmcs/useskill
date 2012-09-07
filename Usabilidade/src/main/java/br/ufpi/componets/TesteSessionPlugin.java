package br.ufpi.componets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Fluxo;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.models.TipoConvidado;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.Implement.FluxoRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaAlternativaRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaEscritaRepositoryImpl;

@SessionScoped
@Component
public class TesteSessionPlugin {
	private Long idTeste;
	private TipoConvidado tipoConvidado;
	private List<ObjetoSalvo> objetosSalvos;
	private final EntityManager entityManager;

	/**
	 * @param entityManager
	 */
	public TesteSessionPlugin(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
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
		System.out.println(objetoSalvo);
		if (objetosSalvos == null) {
			objetosSalvos = new ArrayList<ObjetoSalvo>();
		}
		objetosSalvos.add(objetoSalvo);
	}

	/**
	 * Classe que ira remover todos os objetos de um teste não completo. Ao
	 * remover ele instancia uma nova lista de objetos
	 */
	@PreDestroy
	public void removerObjetosSalvos() {

		for (ObjetoSalvo objetoSalvo : objetosSalvos) {
			System.out.println(objetoSalvo);
			switch (objetoSalvo.getEnumObjetoSalvo()) {
			case FLUXO:
				FluxoRepository fluxoRepository = new FluxoRepositoryImpl(
						entityManager);
				Fluxo fluxo = new Fluxo();
				fluxo.setId(objetoSalvo.getId());
				fluxoRepository.destroy(fluxo);
				break;
			case OBJETIVA:
				RespostaAlternativaRepository respostaAlternativaRepository = new RespostaAlternativaRepositoryImpl(
						entityManager);
				RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
				respostaAlternativa.setId(objetoSalvo.getId());
				respostaAlternativaRepository.destroy(respostaAlternativa);
				break;
			case SUBJETIVA:
				RespostaEscritaRepository respostaEscritaRepository = new RespostaEscritaRepositoryImpl(
						entityManager);
				RespostaEscrita respostaEscrita = new RespostaEscrita();
				respostaEscrita.setId(objetoSalvo.getId());
				respostaEscritaRepository.destroy(respostaEscrita);
				break;
			default:
				break;
			}
		}
		objetosSalvos = new ArrayList<ObjetoSalvo>();
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
