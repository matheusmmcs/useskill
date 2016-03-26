package br.ufpi.datamining.analisys.kmeans;

import java.util.List;

import br.ufpi.datamining.utils.StatisticsUtils;

public class ResultKMeans {

	private int numClusters;
	private double distanceThreshold;
	private List<PointKMeans> centroids;
	private List<PointKMeans> points;
	private List<ClusterKMeans> clusters;
	
	public ResultKMeans(int numClusters, double distanceThreshold,
			List<PointKMeans> centroids, List<PointKMeans> points, List<ClusterKMeans> clusters) {
		super();
		this.numClusters = numClusters;
		this.distanceThreshold = distanceThreshold;
		this.centroids = centroids;
		this.points = points;
		this.setClusters(clusters);
	}
	
	public int getNumClusters() {
		return numClusters;
	}
	public void setNumClusters(int numClusters) {
		this.numClusters = numClusters;
	}
	public double getDistanceThreshold() {
		return distanceThreshold;
	}
	public void setDistanceThreshold(double distanceThreshold) {
		this.distanceThreshold = distanceThreshold;
	}
	public List<PointKMeans> getCentroids() {
		return centroids;
	}
	public void setCentroids(List<PointKMeans> centroids) {
		this.centroids = centroids;
	}
	public List<PointKMeans> getPoints() {
		return points;
	}
	public void setPoints(List<PointKMeans> points) {
		this.points = points;
	}

	public List<ClusterKMeans> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterKMeans> clusters) {
		this.clusters = clusters;
	}
	
}
