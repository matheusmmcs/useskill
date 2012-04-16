package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.Paginacao;

/**
 * @author Cleiton
 *
 */
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
	Teste getTestCriado(Long idUsuario, Long idTeste);

	
	/** Retorna todos os Testes que estaão com o campo liberado igual a falso
	 * @param idUsuario identificador do usuario pertencente ao teste
	 * @param idTeste identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste getTestCriadoNaoLiberado(Long idUsuario, Long idTeste);

	/** Retorna todos os Testes que estaão com o campo Liberado igual a true
	 * @param idUsuario identificador do usuario pertencente ao teste
	 * @param idTeste identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste getTestCriadoLiberado(Long idUsuario, Long idTeste);

	/**
	 * Lista de todos os usuarios que podem participar de um teste. Vai mostrar
	 * apenas os usuarios que ainda não forma convidados.
	 * 
	 * @param testeId
	 * @return Lista de usuarios que podem participar do teste
	 */
	Paginacao<Usuario> usuariosLivresParaPartciparTeste(Long testeId, int numeroPagina,
			int quantidade);

	/**
	 * Relata todos os usuarios que participaram de um teste
	 * @param testeId
	 * @param numeroPagina
	 * @param quantidade
	 * @return
	 */
	Paginacao<Usuario> getUsuariosConvidados(Long testeId, int numeroPagina,
			int quantidade);
	/**Procura por todos os usuarios convidados para um teste.
	 * @param testeId
	 * @return
	 */
	List< Usuario> getUsuariosConvidadosAll(Long testeId);
	/***
	 * 
	 * Lista todos os testes que um usuario foi convidado para participar
	 * @param usuarioId Mostra todos os teste participados de um usuario
	 * @return Lista de testes que o usuario participou
	 * @param quantidade numero de Usuarios por paginas
	 * @param numeroPagina numero da página que sera carregada
	 */
	
	Paginacao<Teste> getTestesParticipados(Long usuarioId, int quantidade, int numeroPagina); 
}
