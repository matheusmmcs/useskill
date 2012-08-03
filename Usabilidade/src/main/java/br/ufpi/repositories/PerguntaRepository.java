package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;

public interface PerguntaRepository {
	void create(Pergunta entity);

	Pergunta update(Pergunta entity);

	void destroy(Pergunta entity);

	Pergunta find(Long id);

	List<Pergunta> findAll();

	/**
	 * Procura se uma pergunta pertence a um determinado Teste e a um
	 * determinado Usuario
	 * 
	 * @param usuarioId
	 * @param testeId
	 * @param perguntaId
	 * @param testeLiberado
	 *            indica se o teste esta liberado ou não
	 * @return Pergunta se obedecer todos os pre-requisitos e null se não
	 *         encontra a pergunta
	 */
	Pergunta perguntaPertenceUsuario(Long usuarioId, Long testeId,
			Long perguntaId, Boolean testeLiberado);

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

	/**
	 * Procura por pergunta que pertence a uma determinada tarefa;
	 * 
	 * @param usuarioId
	 * @param testeId
	 * @param perguntaId
	 * @param tarefaId
	 * @return
	 */
	Pergunta perguntaPertenceUsuarioETarefa(Long usuarioId, Long testeId,
			Long perguntaId, Long tarefaId);

}
