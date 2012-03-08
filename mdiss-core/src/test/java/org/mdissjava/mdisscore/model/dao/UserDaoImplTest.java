package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserDaoImplTest {

	private Session session ;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void setUp() throws Exception {
		HibernateUtil.openSessionFactory();
		session = HibernateUtil.getSession();
	}

	@Test
	public void addUserTest(){		
		this.logger.info("TEST(UserDao) addUser");
	
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

		UserDao dao = new UserDaoImpl();
		dao.addUser(user);				
		assertEquals(user, session.get(User.class, user.getId()));				
	}
	
	@Test
	public void deleteUserTest(){

		
		this.logger.info("TEST(UserDao) addUser");
		
		Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("Prueba");
		user.setName("Prueba");		
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
		
		UserDao dao = new UserDaoImpl();
		
		dao.addUser(user);
		assertEquals(user, session.get(User.class, user.getId()));				

		dao.deleteUser(user);				
		assertNull(session.get(User.class, user.getId()));		
		
		
	}

}
