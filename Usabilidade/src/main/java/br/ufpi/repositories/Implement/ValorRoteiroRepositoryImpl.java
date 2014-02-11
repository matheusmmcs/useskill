/**
 * 
 */
package br.ufpi.repositories.Implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.models.enums.SituacaoDeUsoEnum;
import br.ufpi.models.roteiro.ValorRoteiro;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.ValorRoteiroRepository;

/**
 * @author Matheus
 *
 */
@Component
public class ValorRoteiroRepositoryImpl extends Repository<ValorRoteiro, Long> implements
ValorRoteiroRepository {

	public ValorRoteiroRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<ValorRoteiro> findValorByVariavelInSituacao(Long idVariavel, SituacaoDeUsoEnum situacao) {
		Query query = entityManager.createNamedQuery("ValorRoteiro.findByVariavel.Situacao");
		query.setParameter("variavel", idVariavel);
		query.setParameter("situacao", situacao);
		
		try {
			return (List<ValorRoteiro>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public List<ValorRoteiro> findValorByVariavelAndUsuarioInSituacao(Long idUsuario, Long idVariavel, SituacaoDeUsoEnum situacao) {
		Query query = entityManager.createNamedQuery("ValorRoteiro.findByVariavel.Usuario.Situacao");
		query.setParameter("usuario", idUsuario);
		query.setParameter("variavel", idVariavel);
		query.setParameter("situacao", situacao);
		
		try {
			return (List<ValorRoteiro>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ValorRoteiro> findValorByTarefaAndUsuarioInSituacao(Long idUsuario, Long idTarefa, SituacaoDeUsoEnum situacao) {
		Query query = entityManager.createNamedQuery("ValorRoteiro.findByTarefa.Usuario.Situacao");
		query.setParameter("usuario", idUsuario);
		query.setParameter("tarefa", idTarefa);
		query.setParameter("situacao", situacao);
		
		try {
			return (List<ValorRoteiro>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ValorRoteiro> findValorByTesteAndUsuarioInSituacao(Long idUsuario, Long idTeste, SituacaoDeUsoEnum situacao) {
		Query query = entityManager.createNamedQuery("ValorRoteiro.findByTeste.Usuario.Situacao");
		query.setParameter("usuario", idUsuario);
		query.setParameter("teste", idTeste);
		query.setParameter("situacao", situacao);
		
		try {
			return (List<ValorRoteiro>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
