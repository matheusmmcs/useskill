package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Fluxo;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.Repository;

@Component
public class FluxoRepositoryImpl extends Repository<Fluxo, Long>
		implements FluxoRepository {

	public FluxoRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
