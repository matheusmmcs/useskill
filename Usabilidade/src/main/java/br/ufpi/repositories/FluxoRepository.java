package br.ufpi.repositories;

import java.util.List;


import br.ufpi.models.Fluxo;


public interface FluxoRepository {

		void create(Fluxo entity);

		Fluxo update(Fluxo entity);

		void destroy(Fluxo entity);

		Fluxo find(Long id);

		List<Fluxo> findAll();
		
		Fluxo last();

}
