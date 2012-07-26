package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.repositories.FluxoIdealRepository;
import br.ufpi.repositories.Repository;

@Component
public class FluxoIdealRepositoryImpl extends Repository<FluxoIdeal, Long>
		implements FluxoIdealRepository {

	public FluxoIdealRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
