package br.ufpi.datamining.analisys.kmeans;

import java.util.List;

public class ClusterKMeans {

	private int cluster;
	private List<PointKMeans> points;
	private boolean isBest;
	
	public ClusterKMeans(int cluster, List<PointKMeans> points, boolean isBest) {
		this.cluster = cluster;
		this.points = points;
		this.isBest = isBest;
	}
	
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
	public List<PointKMeans> getPoints() {
		return points;
	}
	public void setPoints(List<PointKMeans> points) {
		this.points = points;
	}

	public boolean isBest() {
		return isBest;
	}

	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}
	
}
