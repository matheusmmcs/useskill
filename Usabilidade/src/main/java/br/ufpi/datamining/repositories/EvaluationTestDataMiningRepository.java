package br.ufpi.datamining.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.repositories.Repository;

@Component
public class EvaluationTestDataMiningRepository extends Repository<EvaluationTestDataMining, Long> {

	public EvaluationTestDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
	
	@SuppressWarnings("unchecked")
	public List<EvaluationTestDataMining> getEvaluationTests(TestDataMining test, Date init, Date end) {
		
		Query query = entityManager.createNamedQuery("Eval.Test.ContainsDates");
		query.setParameter("test", test);
		query.setParameter("initDate", init);
		query.setParameter("lastDate", end);
		
		try {
			return (List<EvaluationTestDataMining>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
