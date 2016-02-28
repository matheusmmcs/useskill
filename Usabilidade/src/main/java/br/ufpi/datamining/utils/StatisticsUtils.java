package br.ufpi.datamining.utils;

import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class StatisticsUtils {

    public static double getMean(double[] ds){
        double sum = 0.0;
        for(double a : ds){
        	sum += a;
        }
        if (ds.length > 0) {
        	return sum/ds.length;
        }else {
        	return 0;
        }
    }
    
    public static double getVarianceSample(double[] data){
        double mean = getMean(data);
        double sumExp = 0;
        for(double a :data){
        	sumExp += a*a;
        }
        Double res = (sumExp-(data.length*(mean*mean)))/(data.length-1);
        return res.equals(Double.NaN) ? 0d : res;
    }

    public static double getVariancePopulation(double[] data){
        double mean = getMean(data);
        double sumExp = 0;
        for(double a :data){
        	sumExp += a*a;
        }
        Double res = (sumExp/data.length) - (mean*mean);
        return res.equals(Double.NaN) ? 0d : res;
    }

    public static double getStdDevPopulation(double[] data){
    	//StandardDeviation sSample = new StandardDeviation(true);
    	//StandardDeviation sPopul = new StandardDeviation(false);
    	Double s = Math.sqrt(getVariancePopulation(data));
    	
    	//System.out.println(sSample.evaluate(data));
    	//System.out.println(sPopul.evaluate(data));
    	System.out.println(s);
    	
    	return s.equals(Double.NaN) ? 0d : s;
    }
    
    public static double getStdDevSample(double[] data){
        return Math.sqrt(getVarianceSample(data));
    }

    public static double median(double[] data) {
       Arrays.sort(data);
       if (data.length % 2 == 0){
          return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
       }else{
          return data[data.length / 2];
       }
    }
}
