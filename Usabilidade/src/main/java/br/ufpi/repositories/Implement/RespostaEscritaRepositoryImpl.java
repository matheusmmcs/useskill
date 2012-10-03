package br.ufpi.repositories.Implement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.models.vo.RespostaEscritaVO;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.util.Paginacao;
@Component
public class RespostaEscritaRepositoryImpl extends
		Repository<RespostaEscrita, Long> implements RespostaEscritaRepository {
	public RespostaEscritaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	/* (non-Javadoc)
	 * @see br.ufpi.repositories.RespostaEscritaRepository#findResposta(java.lang.Long, int, int)
	 */
	@Override
	public Paginacao<RespostaEscritaVO> findResposta(Long perguntaId,
			int numeroPagina, int quantidade) {
		Paginacao<RespostaEscritaVO> paginacao = new Paginacao<RespostaEscritaVO>();
		Query query = entityManager.createNamedQuery("RespostaEscrita.findPergunta");
		query.setParameter("pergunta", perguntaId);
		query.setFirstResult(quantidade * (numeroPagina - 1));
		query.setMaxResults(quantidade);
		paginacao.setListObjects((List<RespostaEscritaVO>) query.getResultList());
		Query count = entityManager.createNamedQuery("RespostaEscrita.findPergunta.Count");
		count.setParameter("pergunta", perguntaId);
		paginacao.setCount((Long) count.getSingleResult());
		return paginacao;
	
	
	}

}
