package br.ufpi.models.vo;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;

public class PerguntaVO {
	private String titulo;
	private String texto;
	private Boolean tipoRespostaAlternativa;
	private List<AlternativaVO> alternativas;

	public PerguntaVO(Pergunta pergunta) {
		super();
		this.titulo = pergunta.getTitulo();
		this.texto = pergunta.getTitulo();
		this.setTipoRespostaAlternativa(pergunta.getTipoRespostaAlternativa());
		this.alternativas = new ArrayList<AlternativaVO>();
		for (Alternativa alternativa : pergunta.getAlternativas()) {
			AlternativaVO alternativaVO = new AlternativaVO(
					alternativa.getId(), alternativa.getTextoAlternativa());
			this.alternativas.add(alternativaVO);
		}
	}

	/**
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            the texto to set
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the alternativas
	 */
	public List<AlternativaVO> getAlternativas() {
		return alternativas;
	}

	/**
	 * @param alternativas
	 *            the alternativas to set
	 */
	public void setAlternativas(List<AlternativaVO> alternativas) {
		this.alternativas = alternativas;
	}

	@Override
	public String toString() {
		return "PerguntaVO [titulo=" + titulo + ", texto=" + texto
				+ ", alternativas=" + alternativas + "]";
	}

	public void setTipoRespostaAlternativa(Boolean tipoRespostaAlternativa) {
		this.tipoRespostaAlternativa = tipoRespostaAlternativa;
	}

	public Boolean getTipoRespostaAlternativa() {
		return tipoRespostaAlternativa;
	}

}
