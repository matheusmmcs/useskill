/**
 * 
 */
package br.ufpi.repositories;

import java.util.List;

import br.ufpi.models.Usuario;
import br.ufpi.models.roteiro.ValorRoteiro;

/**
 * @author Matheus
 *
 */
public interface ValorRoteiroRepository {
	
	void create(ValorRoteiro entity);

	ValorRoteiro update(ValorRoteiro entity);

	void destroy(ValorRoteiro entity);

	ValorRoteiro find(Long id);

	List<ValorRoteiro> findAll();
	
}
