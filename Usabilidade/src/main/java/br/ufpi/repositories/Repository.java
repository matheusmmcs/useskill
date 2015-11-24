package br.ufpi.repositories;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class Repository<T, I extends Serializable> {

	protected final EntityManager entityManager;
	protected final Class<T> clazz;

	protected Repository(EntityManager entityManager) {
		this.entityManager = entityManager;

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		this.clazz = clazz;
	}

	public void create(T entity) {
		entityManager.persist(entity);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public void destroy(T entity) {
		entityManager.remove(entityManager.merge(entity));
	}

	public T find(I id) {
		return entityManager.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public T last() {
		Query query = entityManager.createQuery("from " + clazz.getName() + " as a "+ "order by a.id desc");
		query.setMaxResults(1);
		return (T) query.getSingleResult();
	}

	public List<T> findAll() {
		Query query = entityManager.createQuery("from " + clazz.getName());

		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();

		return resultList;
	}
	
	public Integer count() {
		String queryString = "select Count(*) from " + clazz.getName();
		System.out.println(queryString);
		Query query = entityManager.createQuery(queryString);
		Long count = (Long) query.getSingleResult();
		return count.intValue();
	}

}
