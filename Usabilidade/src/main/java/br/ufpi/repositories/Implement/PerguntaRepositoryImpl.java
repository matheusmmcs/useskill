package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.Repository;

@Component
public class PerguntaRepositoryImpl extends Repository<Pergunta, Long>
		implements PerguntaRepository {

	public PerguntaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Pergunta perguntaPertenceUsuario(Long usuarioId, Long testeId,
			Long perguntaId) {
				Query query = entityManager
				.createNamedQuery("Pergunta.pertence.teste.usuario");
		query.setParameter("teste", testeId);
		query.setParameter("usuario", usuarioId);
		query.setParameter("pergunta", perguntaId);
		try {
			return (Pergunta) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public Questionario findQuestionario(Long idPergunta) {
		try {
			return (Questionario) entityManager
					.createNamedQuery("Pergunta.getQuestionario")
					.setParameter("pergunta", idPergunta).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public boolean deleteAlternativas(Long idPergunta) {
		try {
			 entityManager
					.createNamedQuery("Pergunta.delete.Alternativas")
					.setParameter("pergunta", idPergunta).executeUpdate();

		} catch (NoResultException e) {
			return false;
		}
		return true;

	}

}