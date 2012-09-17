package br.ufpi.repositories;

import br.ufpi.models.Pergunta;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.vo.RespostaAlternativaVO;

import java.util.List;

public interface RespostaAlternativaRepository {

	void create(RespostaAlternativa entity);

	RespostaAlternativa update(RespostaAlternativa entity);

	void destroy(RespostaAlternativa entity);

	RespostaAlternativa find(Long id);

	List<RespostaAlternativa> findAll();

	Pergunta perguntaPertenceTesteLiberadoEAlternativa(Long alternativaId,
			Long perguntaId, Long idTeste);
	/**
	 * Obtem a contagem das respostas de uma alternativa
	 * @param perguntaId identificador da pergunta que o usuario quer obter as respostas
	 * @return
	 */
	List<RespostaAlternativaVO> getRespostasAlternativas(Long perguntaId);
	
}
