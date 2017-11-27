package br.ufpi.datamining.models.vo;

import java.util.List;

import br.ufpi.datamining.models.aux.StackedAreaChart;

public class ChartsVO {
	private List<StackedAreaChart> areaCharts;

	public ChartsVO(List<StackedAreaChart> areaCharts) {
		super();
		this.areaCharts = areaCharts;
	}

	public List<StackedAreaChart> getAreaCharts() {
		return areaCharts;
	}

	public void setAreaCharts(List<StackedAreaChart> areaCharts) {
		this.areaCharts = areaCharts;
	}
	
}
