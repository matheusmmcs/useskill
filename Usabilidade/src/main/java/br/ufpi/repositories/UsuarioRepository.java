package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.Paginacao;

public interface UsuarioRepository {
	/*
	 * Delete the methods you don't want to expose
	 */

	void create(Usuario entity);

	Usuario update(Usuario entity);

	void destroy(Usuario entity);

	Usuario find(Long id);

	Usuario load(Usuario usuario);

	List<Usuario> findAll();

	/**
	 * Procura um usuario pelo email.
	 * 
	 * @param email
	 *            Email de um usuario
	 * @return Usuario que possui email igual
	 */
	Usuario findEmail(String email);

	/**
	 * procura usuario que possui Email e senha igual ao passado.
	 * 
	 * @param email
	 * @param senha
	 * @return Usuario que possui o email e senha passado como paramentro. Se
	 *         usuario não encontrado retorna null.
	 */
	Usuario logar(String email, String senha);

	/**
	 * Procura por usuario que possui confirmação de email igual a repassada.
	 * 
	 * @param confirmacaoEmail
	 * @return Usuario que possui confirmação de email igual ao passado. Se não
	 *         tiver usuario retorna null.
	 */
	Usuario findConfirmacaoEmail(String confirmacaoEmail);

	/**
	 * Devolve todos os testes criados de um usuario.
	 * 
	 * @param usuario
	 * @return
	 */
	List<Teste> findTesteCriados(Usuario usuario);

	/**
	 * Devolve todos os testes criados e ainda não liberados de um usuario por
	 * ordem inversa de criação. Os testes criados primeiro serão mostrados por
	 * ultimo.
	 * 
	 * 
	 * @param usuario
	 * @return
	 */
	Paginacao<Teste> findTesteNaoLiberadosOrdenadorData(Long idUsuario,
			int numeroPagina, int quantidade);

	/**
	 * Procura por todos os testes participados de um usuario
	 * 
	 * @param usuario
	 * @return
	 */
	List<Teste> findTestesParticipados(Usuario usuario);

	/**
	 * Analisa se no banco de dados contem o email inserido.
	 * 
	 * @param email
	 *            Email que esta procurando.
	 * @return True se contem o email busacado
	 */
	boolean isContainsEmail(String email);

	/**
	 * Analisa se algum usuario já possui confirmação de email igual.
	 * 
	 * @param confirmacaoEmail
	 * @author Cleiton analisa se a String buscada, possui algum usuario com a
	 *         mesma.
	 * @return True se já possuir este email e False caso contrario.
	 */
	boolean isContainConfirmacaoEmail(String confirmacaoEmail);

	

}
