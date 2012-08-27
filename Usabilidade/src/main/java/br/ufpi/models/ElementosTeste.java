package br.ufpi.models;

public class ElementosTeste {
	private Long id;
	/**
	 * P - para tipo pergunta T - para tipo tarefas
	 * 
	 */
	private char tipo;
	private String titulo;

	public ElementosTeste(Long id, char tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipo
	 */
	public char getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
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

}
