package br.ufpi.datamining.models.aux;

public class ComparePatternsAux {

	private String patternsBest;
	private String patternsOthers;
	private Long testId;
	private Long evalTestId;
	private Long taskId;
	
	private Double minSup; 
	private Integer minItens;
	
	public ComparePatternsAux() {
		super();
	}
	
	public ComparePatternsAux(String patternsBest, String patternsOthers, Long testId, 
								Long evalTestId, Long taskId, Double minSup, Integer minItens) {
		super();
		this.patternsBest = patternsBest;
		this.patternsOthers = patternsOthers;
		this.testId = testId;
		this.evalTestId = evalTestId;
		this.taskId = taskId;
		this.minSup = minSup;
		this.minItens = minItens;
	}
	
	public String getPatternsBest() {
		return patternsBest;
	}
	public void setPatternsBest(String patternsBest) {
		this.patternsBest = patternsBest;
	}
	public String getPatternsOthers() {
		return patternsOthers;
	}
	public void setPatternsOthers(String patternsOthers) {
		this.patternsOthers = patternsOthers;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getEvalTestId() {
		return evalTestId;
	}

	public void setEvalTestId(Long evalTestId) {
		this.evalTestId = evalTestId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Double getMinSup() {
		return minSup;
	}

	public void setMinSup(Double minSup) {
		this.minSup = minSup;
	}

	public Integer getMinItens() {
		return minItens;
	}

	public void setMinItens(Integer minItens) {
		this.minItens = minItens;
	}
	
	
}
