package br.ufpi.datamining.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.repositories.Repository;

@Component
public class ActionDataMiningRepository extends Repository<ActionDataMining, Long> {

	public ActionDataMiningRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public ActionDataMining getUniqueTest(Long id) {
		Query query = entityManager.createNamedQuery("ActionDataMining.findById");
		query.setParameter("id", id);
		try {
			return (ActionDataMining) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ActionDataMining> getActions(List<FieldSearch> fields){
		if(fields.size() > 0){
			String squery = "SELECT a FROM ActionDataMining a WHERE ";
			int count = 0;
			for(FieldSearch f : fields){
				count++;
				squery += " " + f.getField() + " = :" + f.getField() + " ";
				if(fields.size() > count){
					squery += " AND ";
				}
			}
			//ordenar aqui pode causar problemas, pois ao buscar eventos mais recentes, traz null pointer
			//squery += " ORDER BY a.sTime ASC";
			
			Query query = entityManager.createQuery(squery);
			for(FieldSearch f : fields){
				query.setParameter(f.getField(), f.getValue());
			}
			
			try {
				return query.getResultList();
			} catch (NoResultException e) {}
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ActionDataMining> getUserActions(String username, Long initialId) {
		Query query = entityManager.createQuery("SELECT a FROM ActionDataMining a WHERE a.id >= :initialId AND sUsername = :username ORDER BY a.sTime ASC");
		query.setParameter("initialId", initialId);
		query.setParameter("username", username);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ActionDataMining> getUserActions(String username, Long initialId, Long finalId) {
		Query query = entityManager.createQuery("SELECT a FROM ActionDataMining a WHERE a.id >= :initialId AND a.id <= :finalId AND sUsername = :username ORDER BY a.sTime ASC");
		query.setParameter("initialId", initialId);
		query.setParameter("username", username);
		query.setParameter("finalId", finalId);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
