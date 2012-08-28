package br.ufpi.models.vo;

public class AlternativaVO {
	private Long id;
	private String textoAlternativa;

	public AlternativaVO(Long id, String textoAlternativa) {
		super();
		this.id = id;
		this.textoAlternativa = textoAlternativa;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the textoAlternativa
	 */
	public String getTextoAlternativa() {
		return textoAlternativa;
	}

	/**
	 * @param textoAlternativa the textoAlternativa to set
	 */
	public void setTextoAlternativa(String textoAlternativa) {
		this.textoAlternativa = textoAlternativa;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlternativaVO [id=" + id + ", textoAlternativa="
				+ textoAlternativa + "]";
	}

}
