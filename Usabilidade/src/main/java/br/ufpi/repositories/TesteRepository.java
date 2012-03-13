package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Teste;

public interface TesteRepository {
	void create(Teste entity);

	Teste update(Teste entity);

	void destroy(Teste entity);

	Teste find(Long id);

	List<Teste> findAll();

	/**
	 * Procura por teste criado pelo usuario.
	 * @param idUsuario id do usuario que criou o teste
	 * @param idTeste id do teste procurado
	 * @return null se não encontrar
	 */
	Teste testCriado(Long idUsuario, Long idTeste);

	
	/** Retorna todos os Testes que estaão com o campo testeRealizado igual a falso
	 * @param idUsuario identificador do usuario pertencente ao teste
	 * @param idTeste identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste testCriadoNaoRealizado(Long idUsuario, Long idTeste);

	/** Retorna todos os Testes que estaão com o campo testeRealizado igual a true
	 * @param idUsuario identificador do usuario pertencente ao teste
	 * @param idTeste identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste testCriadoRealizado(Long idUsuario, Long idTeste);
}
