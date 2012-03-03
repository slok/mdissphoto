package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;

import com.google.code.morphia.Datastore;


public class UserDaoImplTest {


	
	
	public void addUserTest(){
		Datastore ds = MorphiaDatastoreFactory.getDatastore("test");
		Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("jess");
		user.setName("Jessica");		
		user.setSurname("Smith");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		
		UserDao dao = new UserDaoImpl(ds);
		dao.addUser(user);		
		assertEquals(user, ds.get(User.class, user.getId()));		
	}
	

	@Test
	public void addLogin(){		
		Datastore ds = MorphiaDatastoreFactory.getDatastore("test");
		
		Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("jess");
		user.setName("Jessica");		
		user.setSurname("Smith");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		user.setEmail("prueba@prueba.com");
		user.setPass("prueba");
		
		HibernateUtil.openSessionFactory();
		UserDao dao = new UserDaoImpl(ds);
		dao.addUser(user);		
		
		//assertEquals(user, ds.get(User.class, user.getId()));		
		
	}

}
