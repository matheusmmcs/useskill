/**
 * 
 */
package br.ufpi.repositories.Implement;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.models.roteiro.ValorRoteiro;
import br.ufpi.repositories.Repository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.ValorRoteiroRepository;

/**
 * @author Matheus
 *
 */
@Component
public class ValorRoteiroRepositoryImpl extends Repository<ValorRoteiro, Long> implements
ValorRoteiroRepository {

	protected ValorRoteiroRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

}
