package br.ufpi.datamining.models.enums;

public enum MomentTypeActionDataMiningEnum {
	START("initial"), REQUIRED("required"), END("end"), DEFAULT("default");
	
	private String desc;
	
	MomentTypeActionDataMiningEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static MomentTypeActionDataMiningEnum getMomentTypeByDesc(String desc){
		for(MomentTypeActionDataMiningEnum t : MomentTypeActionDataMiningEnum.values()){
			if(t.getDesc().equals(desc)){
				return t;
			}
		}
		System.err.println("Momento "+desc+" não encontrado!");
		return null;
	}
}
