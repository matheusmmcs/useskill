package br.ufpi.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Cleiton
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

   

    public static void atualizarBanco() {
        AnnotationConfiguration ac = new AnnotationConfiguration();
        ac.configure();
        SchemaUpdate se = new SchemaUpdate(ac);
        se.execute(true, true);
    }
    public static  void criarBanco(){
        AnnotationConfiguration ac = new AnnotationConfiguration();
        ac.configure();
        SchemaExport se = new SchemaExport(ac);
        se.create(true,true);
    }

}
