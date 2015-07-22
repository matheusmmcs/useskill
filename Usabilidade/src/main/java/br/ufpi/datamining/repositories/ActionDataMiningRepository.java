package br.ufpi.datamining.repositories;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
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
	public List<ActionDataMining> getActions(List<FieldSearch> fields, Set<ActionTypeDataMiningEnum> disregardActions){
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
			if(disregardActions.size() > 0){
				count = 0;
				squery += " AND sActionType NOT IN (";
				for(ActionTypeDataMiningEnum da : disregardActions){
					count++;
					squery += "'" + da.getAction() + "' ";
					if(disregardActions.size() > count){
						squery += ",";
					}
				}
				squery += ") ";
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
	public List<ActionDataMining> getUserActions(String username, Long initialId, Set<ActionTypeDataMiningEnum> disregardActions) {
		String sql = "SELECT a FROM ActionDataMining a WHERE a.sTime >= :initialId AND sUsername = :username";
		if(disregardActions.size() > 0){
			int count = 0;
			sql += " AND sActionType NOT IN (";
			for(ActionTypeDataMiningEnum da : disregardActions){
				count++;
				sql += "'" + da.getAction() + "' ";
				if(disregardActions.size() > count){
					sql += ",";
				}
			}
			sql += ") ";
		}
		sql += " ORDER BY a.sTime ASC";
		Query query = entityManager.createQuery(sql);
		query.setParameter("initialId", initialId);
		query.setParameter("username", username);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ActionDataMining> getUserActions(String username, Long initialId, Long finalId, Set<ActionTypeDataMiningEnum> disregardActions) {
		String sql = "SELECT a FROM ActionDataMining a WHERE a.sTime >= :initialId AND a.sTime <= :finalId AND sUsername = :username";
		if(disregardActions.size() > 0){
			int count = 0;
			sql += " AND sActionType NOT IN (";
			for(ActionTypeDataMiningEnum da : disregardActions){
				count++;
				sql += "'" + da.getAction() + "' ";
				if(disregardActions.size() > count){
					sql += ",";
				}
			}
			sql += ") ";
		}
		sql += " ORDER BY a.sTime ASC";
		Query query = entityManager.createQuery(sql);
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
