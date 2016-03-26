package br.ufpi.datamining.utils;

public class UsabilityUtils {
	
	public static Double calcEffectiveness(Double correctness){
		return correctness;
	}
	
	public static Double calcEfficiency(Double effectiveness, Double qtdAction, Double minActionOkSessions, Double qtdTime, Double minTimeOkSessions){
		return effectiveness /  ((qtdAction/minActionOkSessions) * (qtdTime/minTimeOkSessions));
	}
	
}
