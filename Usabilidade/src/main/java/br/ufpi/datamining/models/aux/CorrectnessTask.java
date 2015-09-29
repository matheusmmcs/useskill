package br.ufpi.datamining.models.aux;

import java.util.HashMap;
import java.util.List;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.PageViewActionDataMining;

public class CorrectnessTask {
	
	public static Double calcCorrectnessSession(List<ActionSingleDataMining> actionsRequired, HashMap<String, Integer> actionsRequiredPresentSession, String actionsRequiredOrder) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
		
		int countActionsInit = 0;
		for(String k : actionsRequiredPresentSession.keySet()){
			countActionsInit += actionsRequiredPresentSession.get(k);
		}
		
		if(countActionsInit == 0){
			return 0d;
		}
		
		HashMap<Long, Integer> countActionsById = new HashMap<Long, Integer>();
		for(ActionSingleDataMining a : actionsRequired){
			PageViewActionDataMining pa = new PageViewActionDataMining(a.toActionDataMining());
			String paKey = pa.getPageViewActionUnique();
			Integer countActions = actionsRequiredPresentSession.get(paKey);
			if(countActions != null){
				countActionsById.put(a.getId(), countActions);
			}else{
				countActionsById.put(a.getId(), 0);
			}
		}
		
		double total = 0, count = 0;
		if(actionsRequiredOrder != null && !actionsRequiredOrder.trim().equals("")){
			String[] groups = actionsRequiredOrder.replaceAll("\\s", "").split(",");
			for(String g : groups){
				if(g.length() > 0){
					if(g.charAt(0) == '['){
						String[] numbers = g.replaceAll("\\[|\\]", "").split(";");
						total++;
						for(String n : numbers){
							if(countActionsById.get(Long.parseLong(n)) > 0){
								//System.out.println(n);
								count++;
								break;
							}
						}
					}else{
						total++;
						if(countActionsById.get(Long.parseLong(g)) > 0){
							//System.out.println(g);
							count++;
						}
						
					}
				}
			}
		}else{
			for(Long l : countActionsById.keySet()){
				if(countActionsById.get(l) > 0){
					count++;
				}
				total++;
			}
		}
		
		return 100*(count/total);
	}
	
}
