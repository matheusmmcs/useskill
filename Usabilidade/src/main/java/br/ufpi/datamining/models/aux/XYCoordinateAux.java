package br.ufpi.datamining.models.aux;

import org.jfree.data.xy.XYDataItem;

public class XYCoordinateAux {

	private double x;
	private double y;
	
	public XYCoordinateAux(XYDataItem xy) {
		super();
		this.setX(xy.getXValue());
		this.setY(xy.getYValue());
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	
}
