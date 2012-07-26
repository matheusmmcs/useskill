package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Acao;

public interface AcaoRepository {
	void create(Acao entity);

	Acao update(Acao entity);

	void destroy(Acao entity);

	Acao find(Long id);

	List<Acao> findAll();
}
