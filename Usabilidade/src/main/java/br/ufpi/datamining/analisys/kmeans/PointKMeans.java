package br.ufpi.datamining.analisys.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
public class PointKMeans {
 
	private String id;
    private double x = 0;
    private double y = 0;
    private int cluster = 0;
    
    //distance to compare to any point and assess if centroid is the best
    private Double eucDist = 0d;
 
    public PointKMeans(String id, double x, double y) {
    	this.setId(id);
        this.setX(x);
        this.setY(y);
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getX()  {
        return this.x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setCluster(int n) {
        this.cluster = n;
    }
    
    public int getCluster() {
        return this.cluster;
    }
    
    //Calculates the distance between two points.
    protected static double distance(PointKMeans p, PointKMeans centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }
    
    //Creates random point
    protected static PointKMeans createRandomPoint(int min, int max) {
    	Random r = new Random();
    	double x = min + (max - min) * r.nextDouble();
    	double y = min + (max - min) * r.nextDouble();
    	return new PointKMeans(String.valueOf(r.nextDouble()), x,y);
    }
    
    protected static List<PointKMeans> createRandomPoints(int min, int max, int number) {
    	List<PointKMeans> points = new ArrayList<PointKMeans>(number);
    	for(int i = 0; i < number; i++) {
    		points.add(createRandomPoint(min,max));
    	}
    	return points;
    }
    
    public String toString() {
    	return id+"-> ("+x+","+y+")";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getEucDist() {
		return eucDist;
	}

	public void setEucDist(Double euclidian_distance) {
		this.eucDist = euclidian_distance;
	}
}
