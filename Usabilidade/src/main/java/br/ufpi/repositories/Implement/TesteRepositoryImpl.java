package br.ufpi.repositories.Implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.models.vo.ConvidadoVO;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.TesteRepository;
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
	public Teste getTestCriadoNaoLiberado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager
				.createNamedQuery("Teste.Criado.NaoLiberado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		try {
			return (Teste) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Teste getTestCriadoLiberado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager.createNamedQuery("Teste.Criado.Liberado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		try {
			return (Teste) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
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
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<ConvidadoVO> getUsuariosConvidados(Long testeId,
			int numeroPagina, int quantidade) {
		Paginacao<ConvidadoVO> paginacao = new Paginacao<ConvidadoVO>();
		Query query = entityManager
				.createNamedQuery("Convidado.Usuarios.Convidados");
		query.setParameter("teste", testeId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects(query.getResultList());
		Query count = entityManager
				.createNamedQuery("Convidado.Usuarios.Convidados.Count");
		count.setParameter("teste", testeId);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConvidadoVO> getUsuariosConvidadosAll(Long testeId) {
		Query query = entityManager
				.createNamedQuery("Convidado.Usuarios.Convidados");
		query.setParameter("teste", testeId);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<Teste> getTestesParticipados(Long usuarioId,
			int quantidade, int numeroPagina) {
		Paginacao<Teste> paginacao = new Paginacao<Teste>();
		Query query = entityManager
				.createNamedQuery("Convidado.Teste.Participado");
		query.setParameter("usuario", usuarioId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects(query.getResultList());
		Query count = entityManager
				.createNamedQuery("Convidado.Teste.Participado.Count");
		count.setParameter("usuario", usuarioId);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<Teste> getTestesCriadosLiberados(Long usuarioId,
			int quantidade, int numeroPagina) {
		Paginacao<Teste> paginacao = new Paginacao<Teste>();
		Query query = entityManager
				.createNamedQuery("Testes.Criados.Liberados");
		query.setParameter("usuarioCriador", usuarioId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects(query.getResultList());
		Query count = entityManager
				.createNamedQuery("Testes.Criados.Liberados.Count");
		count.setParameter("usuarioCriador", usuarioId);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<ConvidadoVO> findTestesConvidados(Long idUsuario, int numeroPagina, int quantidade) {
		Paginacao<ConvidadoVO> paginacao = new Paginacao<ConvidadoVO>();
		Query query =  entityManager.createNamedQuery("Convidado.Teste");
		query.setParameter("usuario", idUsuario);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects((List<ConvidadoVO>) query.getResultList());
		Query count = entityManager
				.createNamedQuery("Convidado.Teste.Count");
		count.setParameter("usuario", idUsuario);
		count.setParameter("realiza", null);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}
}
