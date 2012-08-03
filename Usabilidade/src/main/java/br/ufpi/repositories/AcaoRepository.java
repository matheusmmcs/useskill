package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Action;

public interface AcaoRepository {
	void create(Action entity);

	Action update(Action entity);

	void destroy(Action entity);

	Action find(Long id);

	List<Action> findAll();
}
