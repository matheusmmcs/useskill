package br.ufpi.repositories.Implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.FluxoVO;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.util.Paginacao;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Fluxo> getFluxo(Long testeId, Long tarefaId, Long usarioId,
			Long usuarioCriadorId) {

		Query query = entityManager.createNamedQuery("Fluxo.obterfluxo");
		query.setParameter("teste", testeId);
		query.setParameter("tarefa", tarefaId);
		query.setParameter("usuarioCriador", usuarioCriadorId);
		query.setParameter("usuario", usarioId);
		try {
			return (List<Fluxo>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<FluxoVO> getFluxos(Long tarefaId, Long testeId,
			Long usuarioDonoTeste, int quantidade, int numeroPagina) {
		String namedQuery = "Fluxo.getFluxos.Tarefa";
		Paginacao<FluxoVO> paginacao = new Paginacao<FluxoVO>();
		Query query = entityManager.createNamedQuery(namedQuery);
		query.setParameter("teste", testeId);
		query.setParameter("tarefa", tarefaId);
		query.setParameter("usuarioCriador", usuarioDonoTeste);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects((List<FluxoVO>) query.getResultList());
		Query count = entityManager.createNamedQuery(namedQuery + ".Count");
		count.setParameter("tarefa", tarefaId);
		count.setParameter("teste", testeId);
		count.setParameter("usuarioCriador", usuarioDonoTeste);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getTempoDeTodosFluxos(Long testeId, Long tarefaId,
			Long usarioId, TipoConvidado tipoConvidado) {
		String namedQuery = "Fluxo.getFluxos.Tarefa.Lista.Tempo";
		Query query = entityManager.createNamedQuery(namedQuery);
		query.setParameter("teste", testeId);
		query.setParameter("tarefa", tarefaId);
		query.setParameter("tipoConvidado", tipoConvidado);
		query.setParameter("usuarioCriador", usarioId);
		return (List<Long>)query.getResultList();
	}

}
