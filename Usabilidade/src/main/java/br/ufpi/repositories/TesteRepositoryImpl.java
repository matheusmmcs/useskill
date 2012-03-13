package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Component
public class TesteRepositoryImpl extends Repository<Teste, Long> implements
		TesteRepository {

	public TesteRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Teste testCriado(Long idUsuario, Long idTeste) {
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
	public Teste testCriadoNaoRealizado(Long idUsuario, Long idTeste) {
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
	public Teste testCriadoRealizado(Long idUsuario, Long idTeste) {
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
}
