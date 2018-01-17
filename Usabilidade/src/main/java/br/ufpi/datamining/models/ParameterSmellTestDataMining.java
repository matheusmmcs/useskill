package br.ufpi.datamining.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity  
@Table(name="datamining_parameter_smell_test")
@NamedQueries({
	@NamedQuery(name = "ParameterValue.ValueParameterSmellTest", query = "SELECT p FROM ParameterSmellTestDataMining p WHERE p.test.id = :idTest AND p.parameterSmell.id = :idParameterSmell")
})
public class ParameterSmellTestDataMining implements Serializable {

		private static final long serialVersionUID = 1L;

		private Long id;
		private ParameterSmellDataMining parameterSmell;
		private TestDataMining test;
		private Double value;
		
		@Id  
		@GeneratedValue
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		@ManyToOne
	    @JoinColumn(name="parameter_smell_id")
		public ParameterSmellDataMining getParameterSmell() {
			return parameterSmell;
		}
		
		public void setParameterSmell(ParameterSmellDataMining parameterSmell) {
			this.parameterSmell = parameterSmell;
		}
		
		@Column(nullable = false)
		public Double getValue() {
			return value;
		}
		
		public void setValue(Double value) {
			this.value = value;
		}

		@ManyToOne
	    @JoinColumn(name="test_id")
		public TestDataMining getTest() {
			return test;
		}

		public void setTest(TestDataMining test) {
			this.test = test;
		}

}
