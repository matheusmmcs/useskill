package br.ufpi.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

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
					.createNamedQuery("Pertunta.getQuestionario")
					.setParameter("pergunta", idPergunta).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
