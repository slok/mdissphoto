package org.mdissjava.mdisscore.model.UserTest;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import org.mdissjava.mdisscore.model.DBManager;
import org.mdissjava.mdisscore.model.dao.Login.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.dao.User.*;
import org.mdissjava.mdisscore.model.pojo.*;
import org.mdissjava.mdisscore.model.pojo.User.Gender;

import com.google.code.morphia.Datastore;


public class TestUserDAO {

	private Datastore ds;
	
	@Before
	public void setUp() throws Exception {
		ds = DBManager.getInstance();
	}
	
	
	public void addUserTest(){
		ds = DBManager.getInstance();
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
