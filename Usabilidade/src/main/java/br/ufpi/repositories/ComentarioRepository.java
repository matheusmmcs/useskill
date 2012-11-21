package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Comentario;

public interface ComentarioRepository {
	void create(Comentario entity);

	Comentario update(Comentario entity);

	void destroy(Comentario entity);

	Comentario find(Long id);

	List<Comentario> findAll();
}
