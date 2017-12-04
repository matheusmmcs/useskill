package br.ufpi.datamining.models.vo;

import java.util.List;

import br.ufpi.datamining.models.aux.BarChart;
import br.ufpi.datamining.models.aux.StackedAreaChart;

public class ChartsVO {
	private List<StackedAreaChart> areaCharts;
	private List<BarChart> barCharts; 

	public ChartsVO(List<StackedAreaChart> areaCharts, List<BarChart> barCharts) {
		super();
		this.areaCharts = areaCharts;
		this.barCharts = barCharts;
	}

	public List<StackedAreaChart> getAreaCharts() {
		return areaCharts;
	}

	public void setAreaCharts(List<StackedAreaChart> areaCharts) {
		this.areaCharts = areaCharts;
	}

	public List<BarChart> getBarCharts() {
		return barCharts;
	}

	public void setBarCharts(List<BarChart> barCharts) {
		this.barCharts = barCharts;
	}
	
}
