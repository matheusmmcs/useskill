package br.ufpi.datamining.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity  
@Table(name="datamining_ignored_url")
@NamedQueries({
	@NamedQuery(name = "Ignored.Url", query = "SELECT p FROM IgnoredUrlDataMining p WHERE p.testId = :testId")
})
public class IgnoredUrlDataMining implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String url;
	private Long testId;
	
	@Id
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(nullable = false)
	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

}
