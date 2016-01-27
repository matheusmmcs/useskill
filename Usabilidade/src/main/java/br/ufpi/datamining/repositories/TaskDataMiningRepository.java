package br.ufpi.datamining.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.repositories.Repository;

@Component
public class TaskDataMiningRepository extends Repository<TaskDataMining, Long> {

	public TaskDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<TaskDataMining> getTarefasTeste(Long idTeste) {
		Query query = entityManager.createNamedQuery("Tarefas.teste");
		query.setParameter("testDataMiningId", idTeste);
		try {
			return (List<TaskDataMining>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
