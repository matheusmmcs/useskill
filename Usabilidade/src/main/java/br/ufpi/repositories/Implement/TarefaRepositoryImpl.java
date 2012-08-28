package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Tarefa;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.TarefaRepository;

@Component
public class TarefaRepositoryImpl extends Repository<Tarefa, Long> implements
		TarefaRepository {

	public TarefaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario) {
		Query query = entityManager
				.createNamedQuery("Tarefa.pertence.Teste.Usuario");
		query.setParameter("teste", idTeste);
		query.setParameter("tarefa", idTarefa);
		query.setParameter("usuario", idUsuario);
		try {
			return (Tarefa) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public Tarefa perteceTesteNaoRealizado(Long idTarefa, Long idTeste,
			Long idUsuario) {
		try {
			Query query = entityManager
					.createNamedQuery("Tarefa.pertence.Teste.Nao.Liberado.Usuario");
			query.setParameter("teste", idTeste);
			query.setParameter("tarefa", idTarefa);
			query.setParameter("usuario", idUsuario);
			return (Tarefa) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public String getRoteiro(Long idTarefa, Long idTeste) {
		Query query = entityManager
				.createNamedQuery("Tarefa.pertence.Teste.GetRoteiro");
		query.setParameter("teste", idTeste);
		query.setParameter("tarefa", idTarefa);
		try {
			return (String) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public TarefaVO getTarefaVO(Long idTarefa, Long idTeste) {
		Query query = entityManager
				.createNamedQuery("Tarefa.pertence.Teste.GetTarefaVO");
		query.setParameter("teste", idTeste);
		query.setParameter("tarefa", idTarefa);
		try {
			return (TarefaVO) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
