package br.ufpi.util;

import java.util.List;

import br.com.caelum.vraptor.Result;

/**
 * Usado para fazer paginação de objetos
 * 
 * @author Cleiton
 * 
 * @param <T>
 *            Tipo de objeto que vai ser recebida pela classe
 */
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

	/**
	 * Usado para gerar a paginação de paginas
	 * 
	 * @param numeroPagina
	 *            Informa o numero da página que o usuario esta olhando
	 * @param qtdObjetosPorPaginas
	 *            Informa a quantidade de objetos que sera mostrado por paginas
	 * @param qttObjetosNoBanco
	 *            Informa a quatindade de objetos que possui na base de Dados
	 */
	public void geraPaginacao(int numeroPagina, int qtdObjetosPorPaginas,
			Long qttObjetosNoBanco, Result result) {
		int qtdPaginas = (int) (qttObjetosNoBanco / qtdObjetosPorPaginas);
		if (qttObjetosNoBanco % qtdObjetosPorPaginas != 0) {
			qtdPaginas++;
		}
		result.include("paginaAtual", numeroPagina);
		result.include("qtdPaginas", qtdPaginas);
	}

}
