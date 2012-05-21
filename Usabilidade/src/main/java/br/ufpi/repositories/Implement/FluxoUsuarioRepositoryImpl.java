package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.FluxoUsuario;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.Repository;

@Component
public class FluxoUsuarioRepositoryImpl extends Repository<FluxoUsuario, Long>
		implements FluxoUsuarioRepository {

	protected FluxoUsuarioRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
