package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Alternativa;


public interface AlternativaRepository {
	void create(Alternativa entity);

	Alternativa update(Alternativa entity);

	void destroy(Alternativa entity);

	Alternativa find(Long id);

	List<Alternativa> findAll();

}
