package br.ufpi.datamining.analisys.kmeans;

import java.util.ArrayList;
import java.util.List;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;

public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
    private int numClusters;
    private int seed;
    private List<PointKMeans> points;
    private List<PointKMeans> centroids;
    
    public KMeans(int seed, int numClusters, List<PointKMeans> points) {
    	this.centroids = new ArrayList<PointKMeans>();
    	this.points = points;
    	this.seed = seed;
    	this.numClusters = numClusters;
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate(Instances dataRaw) throws Exception {
    	SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(seed);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(numClusters);
		
		Instances data = new Instances(dataRaw);
		
		//save kmeans points
		List<PointKMeans> newPointsByPos = new ArrayList<PointKMeans>();
		for (int n = 0; n < data.numInstances(); n++) {
			newPointsByPos.add(new PointKMeans(n+"", data.instance(n).value(0), data.instance(n).value(1)));
		}

		kmeans.buildClusterer(data);
 
		System.out.println("--------------------------");
		
		//convert the weka points to initial points 
		int i=0;
		int[] assignments = kmeans.getAssignments();
		for(int clusterNum : assignments) {
			PointKMeans pWeka = getPointKMeansById(i, newPointsByPos);
			
			for (PointKMeans p : this.getPoints()) {
				if (p.getX() == pWeka.getX() && p.getY() == pWeka.getY()) {
					p.setCluster(clusterNum);
					System.out.printf("Instance %d | %s -> Cluster %d \n", i, p.getId(), p.getCluster());
				    System.out.println("Point: " + p.getId() + " ["+p.getX()+";"+p.getY()+"]");
				}
			}
			
		    i++;
		}
		
		//save centroids
		System.out.println("--------------------------");
		Instances instancesCentroids = kmeans.getClusterCentroids();
		for ( int j = 0; j < instancesCentroids.numInstances(); j++ ) {
			Instance inst = instancesCentroids.instance( j );
			PointKMeans centroid = new PointKMeans(j+"", inst.value(0), inst.value(1));
			centroid.setCluster(j);
			this.getCentroids().add(centroid);
		    System.out.println( "Value for centroid " + j + ": " + inst.value(0) + "," + inst.value(1) );
		}
    }
    
    private PointKMeans getPointKMeansById (int id, List<PointKMeans> points) {
    	for (PointKMeans p : points) {
    		if (p.getId().equals(id+"")) {
    			return p;
    		}
    	}
    	return null;
    }
    
    /*
    private PointKMeans getPointKMeansByValues (double x, double y, List<PointKMeans> points) {
    	for (PointKMeans p : points) {
    		if (p.getX() == x && p.getY() == y) {
    			return p;
    		}
    	}
    	return null;
    }
    */
    
    public PointKMeans getCentroid(int clusterNum) {
    	for (PointKMeans p : this.getCentroids()) {
    		if (p.getCluster() == clusterNum) {
    			return p;
    		}
    	}
    	return null;
    }
    
    public List<PointKMeans> getCluster(int clusterNum) {
    	List<PointKMeans> cluster = new ArrayList<PointKMeans>();
    	for (PointKMeans p : this.getPoints()) {
    		if (p.getCluster() == clusterNum) {
    			cluster.add(p);
    		}
    	}
    	return cluster;
    }


	public List<PointKMeans> getCentroids() {
		return centroids;
	}
	

	public List<PointKMeans> getPoints() {
		return points;
	}
    
}
