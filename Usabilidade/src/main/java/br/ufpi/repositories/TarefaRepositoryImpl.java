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

	@Override
	public Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario) {
		return (Tarefa) entityManager.createNamedQuery("Tarefa.pertence.Teste.Usuario").setParameter(0, idTeste).setParameter(1,idTarefa ).setParameter(2, idUsuario).getSingleResult();
	}

	


}
