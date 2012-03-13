package br.ufpi.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

import javax.persistence.EntityManager;
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
		Query query = entityManager.createQuery("Teste.Criado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		return (Teste) query.getSingleResult();
	}

	@Override
	public Teste testCriadoNaoRealizado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager.createQuery("Teste.Criado.NaoRealizado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		return (Teste) query.getSingleResult();
	}

	@Override
	public Teste testCriadoRealizado(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		Query query = entityManager.createQuery("Teste.Criado.Realizado");
		query.setParameter("idteste", idTeste);
		query.setParameter("usuarioCriador", usuario);
		return (Teste) query.getSingleResult();
	}
}
