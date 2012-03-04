package br.ufpi.repositories;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Pergunta;
@Component
public class PerguntaRepositoryImpl  extends Repository<Pergunta, Long> implements
PerguntaRepository {

	protected PerguntaRepositoryImpl(Session session) {
		super(session);
	}

}
