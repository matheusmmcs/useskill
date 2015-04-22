package br.ufpi.datamining.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.repositories.Repository;

@Component
public class ActionDataMiningRepository extends Repository<ActionDataMining, Long> {

	public ActionDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public ActionDataMining getUniqueTest(Long id) {
		Query query = entityManager.createNamedQuery("ActionDataMining.findById");
		query.setParameter("id", id);
//		query.setParameter("senha", senha);
		try {
			return (ActionDataMining) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
