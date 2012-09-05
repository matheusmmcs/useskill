package br.ufpi.repositories;

import javax.persistence.EntityManager;

import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;
import br.ufpi.util.GsonElements;

public class TarefaRepositoryTest extends Repository<Tarefa, Long> implements
		TarefaRepository {
	private TarefaRepositoryImpl tarefaRepositoryImpl;

	public TarefaRepositoryTest(EntityManager entityManager) {
		super(entityManager);
		tarefaRepositoryImpl = new TarefaRepositoryImpl(entityManager);
	}

	@Override
	public void create(Tarefa entity) {
		Teste teste = entity.getTeste();
		TesteRepository testeRepository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		super.create(entity);
		Teste testeUpdate = GsonElements.addTarefa(entity.getId(), teste);
		testeRepository.update(testeUpdate);
	}

	@Override
	public Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario) {
		return tarefaRepositoryImpl.pertenceTeste(idTarefa, idTeste, idUsuario);
	}

	@Override
	public String getRoteiro(Long idTarefa, Long idTeste) {
		return tarefaRepositoryImpl.getRoteiro(idTarefa, idTeste);
	}

	@Override
	public TarefaVO getTarefaVO(Long idTarefa, Long idTeste) {
		return tarefaRepositoryImpl.getTarefaVO(idTarefa, idTeste);
	}

	@Override
	public Tarefa perteceTesteNaoRealizado(Long idTarefa, Long idTeste,
			Long idUsuario) {
		return tarefaRepositoryImpl.perteceTesteNaoRealizado(idTarefa, idTeste,
				idUsuario);
	}

	@Override
	public Fluxo getFluxo(Long testeId, Long tarefaId, Long usarioId,
			Long usuarioCriadorId) {
		return tarefaRepositoryImpl.getFluxo(testeId, tarefaId, usarioId, usuarioCriadorId);
	}

	
}
