package br.ufpi.repositories;

import br.ufpi.models.Convidado;
import br.ufpi.models.Teste;
import java.util.List;

/**
 * Usado para
 * 
 * @author Cleiton
 */
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
	 * 
	 * @param convidados
	 *            Lista de convidados que sera salva
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
	 */
	void convidarUsuarios(List<Long> idUsuarios, Long idTeste);

	/**
	 * Remove o convite de uma lista de usuarios. Deletando o convite do banco
	 * 
	 * @param idUsuarios
	 *            Lista com identificadores dos usuarios que iram ser removidos
	 *            do teste
	 * @param idTeste
	 *            identificador do teste que os usuarios iram desistir
	 */
	void desconvidarUsuarios(List<Long> idUsuarios, Long idTeste);

	/**
	 * Obtem o teste em que o Usuario foi convidado e que o teste já esta
	 * liberado
	 * 
	 * @param testeId
	 *            identificador do teste que o usuario foi convidado
	 * @param usuarioId
	 *            identificador do usuario que vai realizar o teste
	 * @return caso o usuario nao tenha sido convidado retorna null
	 */
	Teste getTesteConvidado(Long testeId, Long usuarioId);

	/**
	 * Obtem uma entidade Convidado
	 * 
	 * @param testeId
	 * @param usuarioId
	 * @return
	 */
	Convidado find(Long testeId, Long usuarioId);
}
