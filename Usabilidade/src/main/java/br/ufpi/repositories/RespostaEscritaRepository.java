package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.RespostaEscrita;

public interface RespostaEscritaRepository {

	void create(RespostaEscrita entity);

	RespostaEscrita update(RespostaEscrita entity);

	void destroy(RespostaEscrita entity);

	RespostaEscrita find(Long id);

	List<RespostaEscrita> findAll();
}
