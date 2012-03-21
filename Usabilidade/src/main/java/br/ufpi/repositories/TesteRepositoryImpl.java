package br.ufpi.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.util.Paginacao;

@Component
public class TesteRepositoryImpl extends Repository<Teste, Long> implements
		TesteRepository {

	public TesteRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Teste getTestCriado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager.createNamedQuery("Teste.Criado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		try {
			return (Teste) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Teste getTestCriadoNaoRealizado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager
				.createNamedQuery("Teste.Criado.NaoRealizado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		try {
			return (Teste) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Teste getTestCriadoRealizado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager.createNamedQuery("Teste.Criado.Realizado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		try {
			return (Teste) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Paginacao<Usuario> usuariosLivresParaPartciparTeste(Long testeId,
			int numeroPagina, int quantidade) {
		Paginacao<Usuario> paginacao = new Paginacao<Usuario>();
		Query query = entityManager
				.createNamedQuery("Convidado.Usuarios.Nao.Convidados");
		query.setParameter("teste", testeId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects((List<Usuario>) query.getResultList());
		Query count = entityManager
				.createNamedQuery("Convidado.Usuarios.Nao.Convidados.Count");
		count.setParameter("teste", testeId);
		count.setFirstResult(quantidade * (numeroPagina - 1));
		count.setMaxResults(quantidade);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}

	@Override
	public Paginacao<Usuario> getUsuariosConvidados(Long testeId,
			int numeroPagina, int quantidade) {
		Paginacao<Usuario> paginacao = new Paginacao<Usuario>();
		Query query = entityManager
				.createNamedQuery("Convidado.Usuarios.Convidados");
		query.setParameter("teste", testeId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects(query.getResultList());
		Query count = entityManager
				.createNamedQuery("Convidado.Usuarios.Convidados.Count");
		count.setParameter("teste", testeId);
		count.setFirstResult(quantidade * (numeroPagina - 1));
		count.setMaxResults(quantidade);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}
}
