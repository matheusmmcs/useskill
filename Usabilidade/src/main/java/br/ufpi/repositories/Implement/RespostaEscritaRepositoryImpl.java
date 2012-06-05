package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.ufpi.models.RespostaEscrita;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.RespostaEscritaRepository;

public class RespostaEscritaRepositoryImpl extends
		Repository<RespostaEscrita, Long> implements RespostaEscritaRepository {
	public RespostaEscritaRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
