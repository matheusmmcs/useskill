package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Pergunta;

public interface PerguntaRepository {
	void create(Pergunta entity);

	Pergunta update(Pergunta entity);

	void destroy(Pergunta entity);

	Pergunta find(Long id);
	List<Pergunta> findAll();

	void saveUpdate(Pergunta pergunta);
}
