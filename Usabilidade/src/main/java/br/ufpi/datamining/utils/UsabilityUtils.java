package br.ufpi.datamining.utils;

public class UsabilityUtils {
	
	public static Double calcEfficiency(Double effectiveness, Double ZscoreAction, Double ZscoreTime){
		return effectiveness / ((ZscoreAction + ZscoreTime) / 2);
	}
	
	public static Double calcEffectiveness(Double completion, Double correctness){
		return (completion * correctness) / 100;
	}
}
