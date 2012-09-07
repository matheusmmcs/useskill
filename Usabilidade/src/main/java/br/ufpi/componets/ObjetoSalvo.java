/**
 * 
 */
package br.ufpi.componets;

import br.ufpi.util.EnumObjetoSalvo;

/**
 * Classe para ser usado quando um teste esta sendo realizado. Caso o teste não
 * seja concluido ele vai deletar todas as informações do teste incompleto
 * 
 * @author Cleiton
 * 
 */
public class ObjetoSalvo {
	private Long id;
	private EnumObjetoSalvo enumObjetoSalvo;

	/**
	 * @param id
	 * @param objetosSalvos
	 */
	public ObjetoSalvo(Long id, EnumObjetoSalvo objetosSalvos) {
		super();
		this.id = id;
		this.setEnumObjetoSalvo(objetosSalvos);
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
	 * @return the enumObjetoSalvo
	 */
	public EnumObjetoSalvo getEnumObjetoSalvo() {
		return enumObjetoSalvo;
	}

	/**
	 * @param enumObjetoSalvo
	 *            the enumObjetoSalvo to set
	 */
	public void setEnumObjetoSalvo(EnumObjetoSalvo enumObjetoSalvo) {
		this.enumObjetoSalvo = enumObjetoSalvo;
	}

	@Override
	public String toString() {
		return "ObjetoSalvo [id=" + id + ", enumObjetoSalvo=" + enumObjetoSalvo
				+ "]";
	}

}
