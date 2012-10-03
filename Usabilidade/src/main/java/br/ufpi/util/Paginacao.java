package br.ufpi.util;

import java.util.HashMap;
import java.util.List;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

import com.google.gson.Gson;

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
	public static final int OBJETOS_POR_PAGINA = 10;

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
	 * @param qtdObjetosNoBanco
	 *            Informa a quatindade de objetos que possui na base de Dados
	 */
	public void geraPaginacao(int numeroPagina, int qtdObjetosPorPaginas,
			Long qtdObjetosNoBanco, Result result) {
		int qtdPaginas = (int) (qtdObjetosNoBanco / qtdObjetosPorPaginas);
		if (qtdObjetosNoBanco % qtdObjetosPorPaginas != 0
				|| qtdObjetosNoBanco == 0) {
			qtdPaginas++;
		}
		result.include("paginaAtual", numeroPagina);
		result.include("qtdPaginas", qtdPaginas);
	}

	/**
	 * Usado para gerar a paginação de paginas com Json
	 * 
	 * @param numeroPagina
	 *            Informa o numero da página que o usuario esta olhando
	 * @param qtdObjetosPorPaginas
	 *            Informa a quantidade de objetos que sera mostrado por paginas
	 * @param qtdObjetosNoBanco
	 *            Informa a quatindade de objetos que possui na base de Dados
	 */
	public void geraPaginacaoJson(int numeroPagina, int qtdObjetosPorPaginas, Long qtdObjetosNoBanco,HashMap<String, Object> mapJson, Result result) {
		int qtdPaginas = (int) (qtdObjetosNoBanco / qtdObjetosPorPaginas);
		Gson gson = new Gson();
		if (qtdObjetosNoBanco % qtdObjetosPorPaginas != 0
				|| qtdObjetosNoBanco == 0) {
			qtdPaginas++;
		}
		mapJson.put("paginaAtual", numeroPagina);
		mapJson.put("qtdPaginas", qtdPaginas);
		result.use(Results.json()).from(gson.toJson(mapJson)).serialize();
	}

	public void geraPaginacao(String nomeObjetosInclud,int numeroPagina,
			Result result) {
		Long qtdObjetosNoBanco=this.getCount();
		int qtdPaginas = (int) (qtdObjetosNoBanco / OBJETOS_POR_PAGINA);
		if (qtdObjetosNoBanco % OBJETOS_POR_PAGINA != 0
				|| qtdObjetosNoBanco == 0) {
			qtdPaginas++;
		}
		result.include("paginaAtual", numeroPagina);
		result.include("qtdPaginas", qtdPaginas);
		result.include(nomeObjetosInclud,this.getListObjects());
		
	}
	

}
