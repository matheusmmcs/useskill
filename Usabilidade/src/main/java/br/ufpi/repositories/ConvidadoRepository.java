package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Convidado;

public interface ConvidadoRepository {
	void create(Convidado entity);

	Convidado update(Convidado entity);

	void destroy(Convidado entity);

	Convidado find(Long id);

	List<Convidado> findAll();

	/**
	 * 
	 * Salva uma lista de Usuarios caso ocorra algum problema não salva nenhum
	 * da Lista de Convidados
	 */
	void salvarLista(List<Convidado> convidados);

	/**
	 * Convida uma Lista de usuarios para participarem de um teste
	 * 
	 * @param idUsuarios
	 *            Lista com identificadores dos usuarios que iram pertencer a um
	 *            teste
	 * @param idTeste
	 *            identificador do teste que os usuarios iram pertencer
	 * @return True se adicionou todos os usuarios ao Teste
	 */
	void convidarUsuarios(List<Long> idUsuarios, Long idTeste);
	/**
	 * Remove o convite de uma lista de usuarios.
	 * Deletando o convite do banco
	 * 
	 * @param idUsuarios
	 *            Lista com identificadores dos usuarios que iram ser removidos do teste
	 * @param idTeste
	 *            identificador do teste que os usuarios iram desistir
	 * @return True se adicionou todos os usuarios ao Teste
	 */
	void desconvidarUsuarios(List<Long> idUsuarios, Long idTeste);
}
