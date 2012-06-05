package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.RespostaAlternativa;

public interface RespostaAlternativaRepository {

	void create(RespostaAlternativa entity);

	RespostaAlternativa update(RespostaAlternativa entity);

	void destroy(RespostaAlternativa entity);

	RespostaAlternativa find(Long id);

	List<RespostaAlternativa> findAll();
}
