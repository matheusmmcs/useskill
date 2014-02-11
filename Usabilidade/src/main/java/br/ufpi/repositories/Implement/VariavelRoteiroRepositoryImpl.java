/**
 * 
 */
package br.ufpi.repositories.Implement;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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

	public VariavelRoteiroRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public VariavelRoteiro findVariavelDaTarefaComNomeIgual(Long idTarefa, String nomeVariavel){
		Query query = entityManager.createNamedQuery("VariavelRoteiro.pertence.Tarefa.com.NomeIgual");
		query.setParameter("tarefa", idTarefa);
		query.setParameter("nomeVariavel", nomeVariavel);
		try {
			return (VariavelRoteiro) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Método que não destroy, apenas seta o boolean deleted para true
	 * @param entity
	 */
	public void delete(VariavelRoteiro entity) {
		entity.setDeleted(true);
		for(ValorRoteiro val : entity.getValores()){
			val.setDeleted(true);
			entityManager.merge(val);
		}
		entityManager.merge(entity);
	}
	
}
