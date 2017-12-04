package br.ufpi.datamining.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.ParameterSmellDataMining;
import br.ufpi.repositories.Repository;

@Component
public class ParameterSmellDataMiningRepository extends Repository<ParameterSmellDataMining, Long> {

	protected ParameterSmellDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
	
	@SuppressWarnings("unchecked")
	public List<ParameterSmellDataMining> getParametersFromSmell(Long idSmell) {
		Query query = entityManager.createNamedQuery("Parameter.Smell");
		query.setParameter("idSmell", idSmell);
		try {
			return (List<ParameterSmellDataMining>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
