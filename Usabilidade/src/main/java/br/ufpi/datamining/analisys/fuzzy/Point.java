/**
 * 
 */
package br.ufpi.datamining.analisys.fuzzy;

/**
 * @author Pedro Almir
 *
 */
public class Point {
	
	private long id;
	private double x;
	private double y;
	private Object info;
	private double[] relevance;
	
	/**
	 * @param id
	 * @param x
	 * @param y
	 */
	public Point(long id, double x, double y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the info
	 */
	public Object getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(Object info) {
		this.info = info;
	}
	/**
	 * @return the relevance
	 */
	public double[] getRelevance() {
		return relevance;
	}
	/**
	 * @param relevance the relevance to set
	 */
	public void setRelevance(double[] relevance) {
		this.relevance = relevance;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [id=" + id + ", x=" + x + ", y=" + y + "]";
	}
}
