package org.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//singleton
public class HibernateFactory {

    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
      return sessionFactory;
    }

    private HibernateFactory(){};

    public static Session getCurrentSessionFromConfig() {
        return getSessionFactory().openSession();
    }
}
