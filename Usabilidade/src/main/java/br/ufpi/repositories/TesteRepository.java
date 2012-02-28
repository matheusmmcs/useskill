package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Teste;

public interface TesteRepository {
	void create(Teste entity);

	Teste update(Teste entity);

	void destroy(Teste entity);

	Teste find(Long id);

	List<Teste> findAll();

	void saveUpdate(Teste entity);
	/**
	 * procura por teste criado pelo usuario
	 * @param idUsuario id do usuario que criou o teste
	 * @param idTeste id do teste procurado
	 * @return Teste caso o teste seja do idUsuario seja o criador do teste e null se não encontrar
	 */
	Teste testCriado(Long idUsuario, Long idTeste);
}
