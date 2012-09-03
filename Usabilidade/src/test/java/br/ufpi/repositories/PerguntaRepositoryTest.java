package br.ufpi.repositories;

import javax.persistence.EntityManager;

import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.vo.PerguntaVO;
import br.ufpi.repositories.Implement.PerguntaRepositoryImpl;
import br.ufpi.util.GsonElements;

public class PerguntaRepositoryTest extends Repository<Pergunta, Long>
		implements PerguntaRepository {
	private PerguntaRepositoryImpl repositoryImpl;

	public PerguntaRepositoryTest(EntityManager entityManager) {
		super(entityManager);
		repositoryImpl = new PerguntaRepositoryImpl(entityManager);
	}

	@Override
	public void create(Pergunta entity) {
		TesteRepository testeRepository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Teste teste = testeRepository.findTestePorQuesTionarioID(entity
				.getQuestionario().getId());
		super.create(entity);
		Teste testeUpdate = GsonElements.addPergunta(entity.getId(), teste);
		testeRepository.update(testeUpdate);
	}

	@Override
	public Pergunta perguntaPertenceUsuario(Long usuarioId, Long testeId,
			Long perguntaId, Boolean testeLiberado) {
		return repositoryImpl.perguntaPertenceUsuario(usuarioId, testeId,
				perguntaId, testeLiberado);
	}

	@Override
	public PerguntaVO perguntaPertenceTeste(Long testeId, Long perguntaId) {
		return repositoryImpl.perguntaPertenceTeste(testeId, perguntaId);
	}

	@Override
	public Questionario findQuestionario(Long idPergunta) {
		return repositoryImpl.findQuestionario(idPergunta);
	}

	@Override
	public boolean deleteAlternativas(Long idPergunta) {
		return repositoryImpl.deleteAlternativas(idPergunta);
	}

}
