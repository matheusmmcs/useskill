package br.ufpi.repositories;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Convidado;
import br.ufpi.models.FluxoUsuario;

@Component
public class FluxoUsuarioRepositoryImpl extends Repository<FluxoUsuario, Long>
		implements FluxoUsuarioRepository {

	protected FluxoUsuarioRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
