package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

public interface PerguntaRepository {
	void create(Pergunta entity);

	Pergunta update(Pergunta entity);

	void destroy(Pergunta entity);

	Pergunta find(Long id);

	List<Pergunta> findAll();

	/**
	 * Procura se uma perguunta pertence a um determinado Teste e a um
	 * determinado Usuario
	 * 
	 * @param usuario
	 * @param teste
	 * @param pergunta
	 * @return Pergunta se obedecer todos os pre-requisitos e null se não
	 *         encontra a pergunta
	 */
	Pergunta perguntaPertenceUsuario(Long usuarioId, Long testeId,
			Long perguntaId);

	/**
	 * @param pergunta
	 * @return
	 */
	/**
	 * Procura por o questionario de uma determinada pergunta
	 * 
	 * @param idPergunta
	 *            Passa uma pergunta com id para retorna a pergunta, se retorna
	 *            null questionario não encontrado.
	 * @return
	 */
	Questionario findQuestionario(Long idPergunta);

	boolean deleteAlternativas(Long idPergunta);

}
