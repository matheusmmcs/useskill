package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.ufpi.models.Comentario;
import br.ufpi.repositories.ComentarioRepository;
import br.ufpi.repositories.Repository;

public class ComentarioRepositoryImpl extends Repository<Comentario, Long>
		implements ComentarioRepository {
	public ComentarioRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
