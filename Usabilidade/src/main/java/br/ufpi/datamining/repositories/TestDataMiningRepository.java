package br.ufpi.datamining.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Repository;

@Component
public class TestDataMiningRepository extends Repository<TestDataMining, Long> {

	public TestDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
	
	public TestDataMining getTestPertencente(Long idUsuario, Long idTeste) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		
		Query query = entityManager.createNamedQuery("Teste.Usuario");
		query.setParameter("test", idTeste);
		query.setParameter("user", usuario);
		try {
			return (TestDataMining) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TestDataMining> getTests(Long idUsuario) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		
		Query query = entityManager.createNamedQuery("Testes.Usuario");
		query.setParameter("user", usuario);
		try {
			return (List<TestDataMining>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
