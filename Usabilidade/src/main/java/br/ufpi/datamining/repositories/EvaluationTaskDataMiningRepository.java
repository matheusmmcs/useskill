package br.ufpi.datamining.repositories;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.repositories.Repository;

@Component
public class EvaluationTaskDataMiningRepository extends Repository<EvaluationTaskDataMining, Long> {

	public EvaluationTaskDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
