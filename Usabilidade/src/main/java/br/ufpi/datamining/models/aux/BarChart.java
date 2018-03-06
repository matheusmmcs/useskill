package br.ufpi.datamining.models.aux;

import java.util.Map;

public class BarChart {
	private String description;
	private String descX;
	private String descY;
	private Map<String, Double> series;
	private Map<String, String> actionsContent;
	
	public BarChart(String description, String descX, String descY, Map<String, Double> series, Map<String, String> actionsContent) {
		super();
		this.description = description;
		this.descX = descX;
		this.descY = descY;
		this.series = series;
		this.actionsContent = actionsContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescX() {
		return descX;
	}

	public void setDescX(String descX) {
		this.descX = descX;
	}

	public String getDescY() {
		return descY;
	}

	public void setDescY(String descY) {
		this.descY = descY;
	}

	public Map<String, Double> getSeries() {
		return series;
	}

	public void setSeries(Map<String, Double> series) {
		this.series = series;
	}

	public Map<String, String> getActionsContent() {
		return actionsContent;
	}

	public void setActionsContent(Map<String, String> actionsContent) {
		this.actionsContent = actionsContent;
	}
	
}
