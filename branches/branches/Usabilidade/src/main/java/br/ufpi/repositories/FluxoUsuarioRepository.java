package br.ufpi.repositories;

import java.util.List;


import br.ufpi.models.FluxoUsuario;

public interface FluxoUsuarioRepository {

		void create(FluxoUsuario entity);

		FluxoUsuario update(FluxoUsuario entity);

		void destroy(FluxoUsuario entity);

		FluxoUsuario find(Long id);

		List<FluxoUsuario> findAll();

}
