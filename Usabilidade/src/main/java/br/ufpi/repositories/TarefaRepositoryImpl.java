package br.ufpi.repositories;


import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
@Component
public class TarefaRepositoryImpl extends Repository<Tarefa, Long> implements
		TarefaRepository {

    public TarefaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

	@Override
	public Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario) {
		try {
			return (Tarefa) entityManager.createNamedQuery("Tarefa.pertence.Teste.Usuario").setParameter(0, idTeste).setParameter(1,idTarefa ).setParameter(2, idUsuario).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	


}
