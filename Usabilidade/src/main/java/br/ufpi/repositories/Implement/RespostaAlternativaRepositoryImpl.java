package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Pergunta;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.RespostaAlternativaRepository;
@Component
public class RespostaAlternativaRepositoryImpl extends Repository<RespostaAlternativa, Long>
		implements RespostaAlternativaRepository {
	public RespostaAlternativaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Pergunta perguntaPertenceTesteLiberadoEAlternativa(
			Long alternativaId, Long perguntaId, Long idTeste) {
			Query query = entityManager.createNamedQuery("Pergunta.pertence.teste.e.alternativa");
			query.setParameter("teste", idTeste);
			query.setParameter("pergunta", perguntaId);
			query.setParameter("alternativa", alternativaId);
			try {
				return (Pergunta) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}

		}	


}
