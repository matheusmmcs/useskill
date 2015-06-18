package br.ufpi.datamining.components;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.ufpi.datamining.models.ActionDataMining;

public class PageViewDataMining {
	private String url;
	private String jhm;
	private String jhmSection;
	private String jhmStep;
	
	public PageViewDataMining(String url, String jhm, String jhmSection, String jhmStep) {
		super();
		this.url = getUrlFormatted(url);
		this.jhm = jhm;
		this.jhmSection = jhmSection;
		this.jhmStep = jhmStep;
	}
	
	public PageViewDataMining(ActionDataMining action){
		this(action.getsUrl(), action.getsJhm(), action.getsSectionJhm(), action.getsStepJhm());
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getJhm() {
		return jhm;
	}
	public void setJhm(String jhm) {
		this.jhm = jhm;
	}
	public String getJhmSection() {
		return jhmSection;
	}
	public void setJhmSection(String jhmSection) {
		this.jhmSection = jhmSection;
	}
	public String getJhmStep() {
		return jhmStep;
	}
	public void setJhmStep(String jhmStep) {
		this.jhmStep = jhmStep;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
            append(this.getUrl()).
            append(this.getJhm()).
            //append(this.getJhmSection()).
            append(this.getJhmStep()).
            toHashCode();
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null){
			return false;
		}	
		if (getClass() != obj.getClass()){
			return false;
		}	
		final PageViewDataMining other = (PageViewDataMining) obj;
		return new EqualsBuilder().
				append(this.getUrl(), other.getUrl()).
	            append(this.getJhm(), other.getJhm()).
	            //append(this.getJhmSection(), other.getJhmSection()).
	            append(this.getJhmStep(), other.getJhmStep()).
	            isEquals();
	}
	
	@Override
	public String toString(){
		return "Url: " + this.getUrl() + " ;Jhm: " + this.getJhm() + " ;Section: " + this.getJhmSection() + " ;Action: " + this.getJhmStep();
	}
	
	public static String getUrlFormatted(String url){
		return url.replaceAll(";jsessionid\\=[A-Z|0-9]*", "").replaceAll(":8080", "").replaceAll("www.", "");
	}
}