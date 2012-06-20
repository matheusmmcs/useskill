package br.ufpi.repositories.Implement;

import br.ufpi.models.Alternativa;
import br.ufpi.repositories.AlternativaRepository;
import br.ufpi.repositories.Repository;
import javax.persistence.EntityManager;

public class AlternativaRepositoryImpl extends Repository<Alternativa, Long>
		implements AlternativaRepository {
	public AlternativaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
