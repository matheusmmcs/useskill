package br.ufpi.repositories;


import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Tarefa;
import javax.persistence.EntityManager;
@Component
public class TarefaRepositoryImpl extends Repository<Tarefa, Long> implements
		TarefaRepository {

    public TarefaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

	


}
