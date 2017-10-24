package br.ufpi.datamining.models.enums;

public enum SessionClassificationDataMiningFilterEnum {
	
	ALL(0), SUCCESS(1), WITH_PROBLEM(2), ERROR(3), REPEAT(4), THRESHOLD(5), SUCCESS_ERROR_REPEAT(6);
	
	private Integer value;
	
	SessionClassificationDataMiningFilterEnum(Integer value){
		this.setValue(value);
	}

	public static SessionClassificationDataMiningFilterEnum getByValue(Integer value){
		for(SessionClassificationDataMiningFilterEnum t : SessionClassificationDataMiningFilterEnum.values()){
			if(t.getValue().equals(value)){
				return t;
			}
		}
		return null;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
