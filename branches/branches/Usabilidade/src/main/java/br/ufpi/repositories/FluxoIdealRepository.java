package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.FluxoIdeal;

public interface FluxoIdealRepository {
	void create(FluxoIdeal entity);

	FluxoIdeal update(FluxoIdeal entity);

	void destroy(FluxoIdeal entity);

	FluxoIdeal find(Long id);

	List<FluxoIdeal> findAll();
}
