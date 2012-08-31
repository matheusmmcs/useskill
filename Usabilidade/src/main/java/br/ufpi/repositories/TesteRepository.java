package br.ufpi.repositories;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.models.vo.ConvidadoVO;
import br.ufpi.models.vo.TesteVO;
import br.ufpi.util.Paginacao;
import java.util.List;

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
	 * 
	 * @param idUsuario
	 *            id do usuario que criou o teste
	 * @param idTeste
	 *            id do teste procurado
	 * @return null se não encontrar
	 */
	Teste getTestCriado(Long idUsuario, Long idTeste);

	/**
	 * Retorna todos os Testes que estaão com o campo liberado igual a falso
	 * 
	 * @param idUsuario
	 *            identificador do usuario pertencente ao teste
	 * @param idTeste
	 *            identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste getTestCriadoNaoLiberado(Long idUsuario, Long idTeste);

	/**
	 * Retorna todos os Testes que estão com o campo Liberado igual a true
	 * 
	 * @param idUsuario
	 *            identificador do usuario pertencente ao teste
	 * @param idTeste
	 *            identificados do teste a ser procurado
	 * @return null caso o teste não seja encontrado
	 */
	Teste getTestCriadoLiberado(Long idUsuario, Long idTeste);

	/**
	 * Lista de todos os usuarios que podem participar de um teste. Vai mostrar
	 * apenas os usuarios que ainda não forma convidados.
	 * 
	 * @param testeId
	 * @param numeroPagina
	 * @param quantidade
	 * @return Lista de usuarios que podem participar do teste
	 */
	Paginacao<Usuario> usuariosLivresParaPartciparTeste(Long testeId,
			int numeroPagina, int quantidade);

	/**
	 * Relata todos os usuarios que participaram de um teste
	 * 
	 * @param testeId
	 * @param numeroPagina
	 * @param quantidade
	 * @return
	 */
	Paginacao<ConvidadoVO> getUsuariosConvidados(Long testeId,
			int numeroPagina, int quantidade);

	/**
	 * Procura por todos os usuarios convidados para um teste.
	 * 
	 * @param testeId
	 * @return
	 */
	List<ConvidadoVO> getUsuariosConvidadosAll(Long testeId);

	/***
	 * 
	 * Lista todos os testes que um usuario foi convidado para participar
	 * 
	 * @param usuarioId
	 *            Mostra todos os teste participados de um usuario
	 * @return Lista de testes que o usuario participou
	 * @param quantidade
	 *            numero de Usuarios por paginas
	 * @param numeroPagina
	 *            numero da página que sera carregada
	 */

	Paginacao<Teste> getTestesParticipados(Long usuarioId, int quantidade,
			int numeroPagina);

	/***
	 * 
	 * Lista todos os testes que um usuario já liberou
	 * 
	 * @param usuarioId
	 *            identificador do usuario que quer seus testes
	 * @param quantidade
	 *            numero de Testes por paginas
	 * @param numeroPagina
	 *            numero da página que sera carregada
	 * @return Lista de testes que o usuario liberou
	 */
	Paginacao<Teste> getTestesCriadosLiberados(Long usuarioId, int quantidade,
			int numeroPagina);

	/**
	 * Retorna todos os testes que o usuario foi convidado para participar
	 * 
	 * @param idUsuario
	 *            Identificador do usuario que foi convidado para participar do
	 *            Teste
	 * @return Retorna todos os testes que o usuario foi convidado a Participar
	 *         e para qual tipo de convite ele foi selecionado
	 */
	Paginacao<ConvidadoVO> findTestesConvidados(Long idUsuario,
			int numeroPagina, int quantidade);

	/**
	 * Retorna todos os testes que o usuario foi convidado para participar
	 * 
	 * @param idUsuario
	 *            Identificador do usuario que foi convidado para participar do
	 *            Teste
	 * @return Retorna todos os testes que o usuario foi convidado a Participar
	 *         e para qual tipo de convite ele foi selecionado
	 */
	Paginacao<TesteVO> findTestesConvidados(int numeroPagina, Long idUsuario,
			int quantidade);

	Teste findTestePorQuesTionarioID(Long idQuestionario);
}
