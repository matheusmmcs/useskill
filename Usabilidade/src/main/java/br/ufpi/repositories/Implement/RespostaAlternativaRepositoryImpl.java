package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.ufpi.models.RespostaAlternativa;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.RespostaAlternativaRepository;

public class RespostaAlternativaRepositoryImpl extends Repository<RespostaAlternativa, Long>
		implements RespostaAlternativaRepository {
	public RespostaAlternativaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}


}
