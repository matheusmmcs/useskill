package br.ufpi.datamining.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityDefaultManagerUtil {

	private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("default");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
	
}
