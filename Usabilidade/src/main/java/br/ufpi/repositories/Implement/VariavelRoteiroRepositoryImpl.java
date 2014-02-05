/**
 * 
 */
package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Usuario;
import br.ufpi.models.roteiro.ValorRoteiro;
import br.ufpi.models.roteiro.VariavelRoteiro;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.ValorRoteiroRepository;
import br.ufpi.repositories.VariavelRoteiroRepository;

/**
 * @author Matheus
 *
 */
@Component
public class VariavelRoteiroRepositoryImpl extends Repository<VariavelRoteiro, Long> implements
VariavelRoteiroRepository {

	protected VariavelRoteiroRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
}
