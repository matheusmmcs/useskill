package br.ufpi.util;

import java.util.List;

public class Paginacao<T> {
	private Long count;
	private List<T> listObjects;

	public Paginacao() {
		super();
	}

	public Paginacao(Long count, List<T> listObjects) {
		super();
		this.setCount(count);
		this.setListObjects(listObjects);
	}

	public List<T> getListObjects() {
		return listObjects;
	}

	public void setListObjects(List<T> listObjects) {
		this.listObjects = listObjects;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
