package br.ufpi.models.enums;

public enum TipoAcaoEnum {
	
	//Eventos na página
	CLICK ("click", "Clique"), 
	FOCUSOUT ("focusout", "Perda de Foco"),
	MOUSEOVER ("mouseover", "Mouse Sobre"),

	//Eventos da UseSkill
	ROTEIRO ("roteiro", "Ver Roteiro"),
	CONCLUIR ("concluir", "Concluir"),
	COMENTARIO ("comentario", "Comentar"),
	PULAR ("pular", "Pular"),
	
	//Eventos do browser para mudança de páginas
	FORWARD_BACK ("forward_back", "Botão Voltar/Avançar"),
	LINK ("link", "Página de Link"),
	FORM_SUBMIT ("form_submit", "Página de Formulário"),
	TYPED ("typed", "Página Digitada"),
	RELOAD ("reload", "Página Recarregada")
	;
	
	/**
	 * Abreviação representa como a ação está persistida no banco
	 */
	private String abreviacao;
	/**
	 * Texto que representa a ação de forma legível
	 */
	private String descricao;
	
	TipoAcaoEnum(String abreviacao, String descricao) {
		this.abreviacao = abreviacao;
		this.descricao = descricao;
	}
	
	public String getAbreviacao(){
		return abreviacao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
}
