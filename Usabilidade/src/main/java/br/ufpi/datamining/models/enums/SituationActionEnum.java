package br.ufpi.datamining.models.enums;

public enum SituationActionEnum {
	OK("ok"),
	ERROR("error"),
	DEFAULT("default");
	
	private String desc;
	
	SituationActionEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
