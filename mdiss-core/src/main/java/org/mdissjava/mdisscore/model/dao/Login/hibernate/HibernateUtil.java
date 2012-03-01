package org.mdissjava.mdisscore.model.dao.Login.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;



public class HibernateUtil {
   private static SessionFactory sessionFactory;
   /**
        * @uml.property  name="session"
        */
   private static Session session;
   
   public static void openSessionFactory() {
           
	   	sessionFactory = new AnnotationConfiguration().configure("/org/mdissjava/mdisscore/model/dao/Login/hibernate/hibernate.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
   }

   /**
        * @return
        * @uml.property  name="session"
        */
   public static Session getSession() {
       return session;
   }
 
}
