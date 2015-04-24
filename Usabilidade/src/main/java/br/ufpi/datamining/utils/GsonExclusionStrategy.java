package br.ufpi.datamining.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStrategy implements ExclusionStrategy {

    private final Class<?>[] typesToSkip;

    public GsonExclusionStrategy(Class<?>... typesToSkip) {
      this.typesToSkip = typesToSkip;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
    	for(Class<?> type : typesToSkip){
    		if(clazz == type){
    			return true;
    		}
    	}
    	return false;
    }

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		// TODO Auto-generated method stub
		return false;
	}
}