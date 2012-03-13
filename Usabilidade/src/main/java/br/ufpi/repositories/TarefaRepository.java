package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Tarefa;

public interface TarefaRepository {
	void create(Tarefa entity);

	Tarefa update(Tarefa entity);

	void destroy(Tarefa entity);

	Tarefa find(Long id);
	List<Tarefa> findAll();

	Tarefa pertenceTeste(Long idTarefa, Long idTeste, Long idUsuario);

}
