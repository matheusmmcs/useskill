package br.ufpi.datamining.models.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.caelum.vraptor.validator.Message;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;

public class ReturnVO {

	private ReturnStatusEnum status;
	private String message;
	private List<String> errors;
	private String urlRedirect;
	
	public ReturnVO() {
		super();
		errors = new ArrayList<String>();
	}

	public ReturnVO(ReturnStatusEnum status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public ReturnStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ReturnStatusEnum status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getUrlRedirect() {
		return urlRedirect;
	}
	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
	}
	public void setErrorsMessage(List<Message> errosmsg){
		errors = new ArrayList<String>();
		for(Message erro : errosmsg){
			errors.add(erro.getMessage());
		}
	}
	
}
