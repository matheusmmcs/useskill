package br.ufpi.datamining.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.IgnoredUrlDataMining;
import br.ufpi.repositories.Repository;

@Component
public class IgnoredUrlDataMiningRepository extends Repository<IgnoredUrlDataMining, String>{

	public IgnoredUrlDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
	
	@SuppressWarnings("unchecked")
	public List<IgnoredUrlDataMining> getIgnoredUrls(Long testId) {
		Query query = entityManager.createNamedQuery("Ignored.Url");
		query.setParameter("testId", testId);
		try {
			return (List<IgnoredUrlDataMining>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
