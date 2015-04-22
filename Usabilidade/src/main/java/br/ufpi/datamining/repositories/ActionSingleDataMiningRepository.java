package br.ufpi.datamining.repositories;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.repositories.Repository;

@Component
public class ActionSingleDataMiningRepository extends Repository<ActionSingleDataMining, Long> {

	public ActionSingleDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}

}
