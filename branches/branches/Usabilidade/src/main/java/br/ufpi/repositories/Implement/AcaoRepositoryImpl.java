package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.ufpi.models.Acao;
import br.ufpi.repositories.AcaoRepository;
import br.ufpi.repositories.Repository;

public class AcaoRepositoryImpl extends Repository<Acao, Long>
		implements AcaoRepository {
	public AcaoRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
