package br.ufpi.repositories;

import br.ufpi.models.RespostaEscrita;
import java.util.List;

public interface RespostaEscritaRepository {

	void create(RespostaEscrita entity);

	RespostaEscrita update(RespostaEscrita entity);

	void destroy(RespostaEscrita entity);

	RespostaEscrita find(Long id);

	List<RespostaEscrita> findAll();
}
