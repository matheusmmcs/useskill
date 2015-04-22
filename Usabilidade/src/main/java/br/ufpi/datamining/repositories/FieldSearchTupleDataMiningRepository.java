package br.ufpi.datamining.repositories;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.repositories.Repository;

@Component
public class FieldSearchTupleDataMiningRepository extends Repository<FieldSearchTupleDataMining, Long> {

	public FieldSearchTupleDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
