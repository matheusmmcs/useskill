package br.ufpi.repositories;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;

@Component
public class TesteRepositoryImpl extends Repository<Teste, Long> implements
		TesteRepository {

	protected TesteRepositoryImpl(Session session) {
		super(session);
	}

}
