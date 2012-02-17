package br.ufpi.repositories;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Tarefa;
@Component
public class TarefaRepositoryImpl extends Repository<Tarefa, Long> implements
		TarefaRepository {

	protected TarefaRepositoryImpl(Session session) {
		super(session);
	}


}
