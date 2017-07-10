/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Misaina
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
//    public SessionFactory buildSessionFactory(){
//        SessionFactory sessionFactory=null;
//            
//        try {
//
//            sessionFactory = new Configuration().configure().buildSessionFactory();
//            
//            return sessionFactory;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
