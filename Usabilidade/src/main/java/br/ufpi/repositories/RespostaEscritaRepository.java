package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.RespostaEscrita;
import br.ufpi.models.vo.RespostaEscritaVO;
import br.ufpi.util.Paginacao;

public interface RespostaEscritaRepository {

	void create(RespostaEscrita entity);

	RespostaEscrita update(RespostaEscrita entity);

	void destroy(RespostaEscrita entity);

	RespostaEscrita find(Long id);

	List<RespostaEscrita> findAll();

	/**
	 * Obtem as respostas escritas de uma pergunta em forma de paginação
	 * @param perguntaId
	 * @param numeroPagina
	 * @param quantidade
	 * @return
	 */
	Paginacao<RespostaEscritaVO> findResposta(Long perguntaId,
			int numeroPagina, int quantidade);
}
