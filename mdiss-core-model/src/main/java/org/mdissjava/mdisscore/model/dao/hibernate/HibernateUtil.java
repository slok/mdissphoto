package org.mdissjava.mdisscore.model.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HibernateUtil {

   private static SessionFactory sessionFactory = null;
   private static Session session = null;
   private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
   
   private static void openSessionFactory() {
       Configuration configuration = new Configuration();
       configuration.configure();
       ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
       sessionFactory = configuration.buildSessionFactory(serviceRegistry);
       logger.info("Creating session factory");

   }


   public static Session getSession() {
	  
	   if (sessionFactory == null)
		   openSessionFactory();
	    
	  //automated
      //return sessionFactory.getCurrentSession();

      if (session == null || !session.isConnected())
      {
    	  logger.info("Openning hibernate session");
    	  session = sessionFactory.openSession();
      }
      else
    	  logger.info("Reusing hibernate session");
      return session;
   }

   
   public static void destroySessionFactory(){
   
	   sessionFactory.close();
	   session.close();
	   session = null;
	   sessionFactory = null;
   }
 
}
