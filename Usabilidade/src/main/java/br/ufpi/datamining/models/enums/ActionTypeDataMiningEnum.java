package br.ufpi.datamining.models.enums;

public enum ActionTypeDataMiningEnum {
	onload ("onload", "Carregamento"),
	click("click", "Clique"),
	form_submit("form_submit", "Envio de Formulário"),
	focusout("focusout", "Preenchimento de Campo"),
	mouseover("mouseover", "Mouse Sobre"),
	back("back", "Voltar"),
	reload("reload", "Recarregar");
	
	private String action;
	private String description;
	
	ActionTypeDataMiningEnum(String action, String description){
		this.setAction(action);
		this.setDescription(description);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static ActionTypeDataMiningEnum getTypeActionByAction(String action){
		for(ActionTypeDataMiningEnum t : ActionTypeDataMiningEnum.values()){
			if(t.getAction().equals(action)){
				return t;
			}
		}
		System.err.println("Action "+action+" não encontrada!");
		return null;
	}
}
