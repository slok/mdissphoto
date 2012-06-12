package org.mdissjava.mdisscore.model.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private UserDao userdao;
	

	@Before
	public void init() {
		userdao = new UserDaoImpl();
	}
	
	
	@After
	public void destroy() {		
		userdao = null;
	}
	
	public User createUserA(){
		Address address = new Address();		
		address.setStreet("Madariaga 6");					
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("Cheseal");
		user.setActive(true);
		user.setName("Jessica");		
		user.setSurname("Smith");
		user.setPhone(944655874);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		user.setEmail("prueba@prueba.com");
		user.setPass("prueba");	
		return user;
	}
		
	public User createUserB(){
		AddressDao dao = new AddressDaoImpl();
		Country c = dao.getCountry((short) 100);
		City city = dao.getCity(986);
		Address address = new Address();
		address.setStreet("Avda Universidades");
		address.setCountry(c);
		address.setCity(city);
		
		Configuration conf = new Configuration();
		
		User user = new User();
		user.setNick("mdissWorarkarfer2");
		user.setName("Java2");		
		user.setSurname("Master2");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("java");
		user.addPreference("programming");
		user.addPreference("pojos");
		user.setEmail("prueba2@prueba2.com");
		user.setPass("9e2c6781e1d498c41d3b146262158a5803f9724067af0d30e7179856ad66c74f");
		user.setRole("USER");
		user.setActive(true);
		return user;
	}	
	
	@Test
	public void addUserTest(){				
		this.logger.info("[TEST] addUserTest UserDaoImpl");																
		
		User user = createUserA();
		userdao.addUser(user);						
		assertEquals(user, userdao.getUserByNick(user.getNick()));		
		userdao.deleteUser(user);
	}	
	
	@Test
	public void updateUserTest(){		
		this.logger.info("[TEST] updateUserTest UserDaoImpl");
		
		User user = createUserA();
		userdao.addUser(user);
		
		user.setEmail("pruebaUpdateCorrecta2@prueba2.com");		
		userdao.updateUser(user);
		
		assertEquals(user, userdao.getUserByNick(user.getNick()));		
		userdao.deleteUser(user);		
	}
	
	@Test
	public void deleteUserTest(){
		this.logger.info("[TEST] deleteUserTest UserDaoImpl");		
		User user = createUserA();						
		userdao.addUser(user);		
		userdao.deleteUser(user);			
		assertNull(userdao.getUserByNick("Jess01")); 		
	}		
		
	@Test
	public void updateUserConfigurationTest(){
		this.logger.info("[TEST] updateUserConfigurationTest UserDaoImpl");		
		User user = createUserA();			
		userdao.addUser(user);					
		
		user.getConfiguration().setShowName(false);
		user.getConfiguration().setShowPhone(true);
		user.getConfiguration().setShowEmail(true);		
		userdao.updateUser(user);
		
		assertEquals(user, userdao.getUserByNick(user.getNick()));		
		userdao.deleteUser(user);		
	}
			
	@Test
	public void userAlreadyExistsTest() throws Exception{		
		this.logger.info("[TEST] userAlreadyExistsTest UserDaoImpl");		
		
		User user = createUserA();	
		userdao.addUser(user);				
		
		assertTrue(userdao.emailAlreadyExists("prueba@prueba.com"));
		assertTrue(userdao.nickAlreadyExists("Cheseal"));
		assertFalse(userdao.nickAlreadyExists("CrazySurfer"));

		userdao.deleteUser(user);
	}
	
	@Test
	public void addFriendTest(){
		this.logger.info("[TEST] addFriendTest UserDaoImpl");

		User userA = createUserA();
		User userB = createUserA();
		userdao.addUser(userA);				
		userdao.addUser(userB);

		userA.addFollow(userB);
		userdao.updateUser(userA);		
		
		//assertEquals(userB, userdao.findFollows(userA.getNick()).get(0));			
		userdao.deleteUser(userA);
		userdao.deleteUser(userB);
	}
			
	@Test
	public void getUserByNickTest()throws IllegalArgumentException{		
		this.logger.info("[TEST] getUserByNickTest UserDaoImpl");	
		
		User user = createUserB();
		userdao.addUser(user);					
		assertEquals("mdissWorarkarfer2",  userdao.getUserByNick("mdissWorarkarfer2").getNick());		
		userdao.deleteUser(user);
	}

}