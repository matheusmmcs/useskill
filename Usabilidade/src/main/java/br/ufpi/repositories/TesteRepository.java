package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Teste;

public interface TesteRepository {
	void create(Teste entity);

	Teste update(Teste entity);

	void destroy(Teste entity);

	Teste find(Long id);

	List<Teste> findAll();

}
