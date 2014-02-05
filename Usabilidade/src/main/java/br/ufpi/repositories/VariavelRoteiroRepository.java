/**
 * 
 */
package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.roteiro.VariavelRoteiro;

/**
 * @author Matheus
 *
 */
public interface VariavelRoteiroRepository {

	void create(VariavelRoteiro entity);

	VariavelRoteiro update(VariavelRoteiro entity);

	void destroy(VariavelRoteiro entity);

	VariavelRoteiro find(Long id);

	List<VariavelRoteiro> findAll();
	
}
