package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import br.ufpi.models.Comentario;
import br.ufpi.repositories.ComentarioRepository;
import br.ufpi.repositories.Repository;
@Component
public class ComentarioRepositoryImpl extends Repository<Comentario, Long>
		implements ComentarioRepository {
	public ComentarioRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
