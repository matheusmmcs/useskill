package br.ufpi.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.FluxoVO;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;
import br.ufpi.util.GsonElements;
import br.ufpi.util.Paginacao;

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
	public Paginacao<FluxoVO> getFluxos(Long tarefaId, Long testeId,
			Long usuarioDonoTeste, int quantidade, int numeroPagina) {
		
		return tarefaRepositoryImpl.getFluxos(tarefaId, testeId, usuarioDonoTeste, quantidade, numeroPagina);
	}

	@Override
	public List<Fluxo> getFluxo(Long testeId, Long tarefaId, Long usarioId,
			Long usuarioCriadorId) {
		return tarefaRepositoryImpl.getFluxo(testeId, tarefaId, usarioId, usuarioCriadorId);
	}

	/* (non-Javadoc)
	 * @see br.ufpi.repositories.TarefaRepository#getTempoDeTodosFluxos(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Long> getTempoDeTodosFluxos(Long testeId, Long tarefaId,
			Long usarioId,TipoConvidado tipoConvidado) {
		return tarefaRepositoryImpl.getTempoDeTodosFluxos(testeId, tarefaId, usarioId,tipoConvidado);
	}

	/* (non-Javadoc)
	 * @see br.ufpi.repositories.TarefaRepository#getAcoesFluxo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Action> getAcoesFluxo(Long testeId, Long tarefaId,
			Long usarioId, Long usuarioCriadorId, Long fluxoId) {
		return tarefaRepositoryImpl.getAcoesFluxo(testeId, tarefaId, usarioId, usuarioCriadorId, fluxoId);
	}

	
}
