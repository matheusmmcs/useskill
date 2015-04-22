package br.ufpi.datamining.repositories;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.repositories.Repository;

@Component
public class TaskDataMiningRepository extends Repository<TaskDataMining, Long> {

	public TaskDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
