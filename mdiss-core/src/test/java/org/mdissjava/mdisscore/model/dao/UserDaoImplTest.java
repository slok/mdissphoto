package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

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
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;


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
		user.setActive(true);
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

		
		this.logger.info("TEST(UserDao) deleteUser");
		
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

	@Test
	public void getByIdTest()throws IllegalArgumentException{
		
		this.logger.info("TEST(UserDao) getUserByID");

		Address address = new Address();		
		address.setStreet("Avda Universidades");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("MDISS");
		user.setName("Java");		
		user.setSurname("Master");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("java");
		user.addPreference("programming");
		user.addPreference("pojos");
		user.setEmail("prueba@prueba.com");
		user.setPass("mdiss");
		
		UserDao dao = new UserDaoImpl();
		dao.addUser(user);
				
		assertEquals(user,  dao.getUserById(user.getId()));
		
		User user2=dao.getUserById(user.getId());
		if(user2.getEmail()!=user.getEmail())
			throw new IllegalArgumentException("error");
		
	}

	@Test
	public void updateUserTest(){
		
		this.logger.info("TEST(UserDao) updateUser");
		
		Address address = new Address();		
		address.setStreet("Madariaga 64");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
		
		User user = new User();
		user.setNick("jessAgain");
		user.setActive(true);
		user.setName("JessicaAgain");		
		user.setSurname("SmithAgain");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Female);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		user.setEmail("pruebaUpdate@prueba.com");
		user.setPass("prueba");
		
		UserDao dao = new UserDaoImpl();
		dao.addUser(user);
		
		user.setEmail("pruebaUpdateCorrecta@prueba.com");
		
		dao.updateUser(user);
		
		assertEquals(user, session.get(User.class, user.getId()));
		
	}
	
	@Test
	public void updateUserAdressTest(){
		this.logger.info("TEST(UserDao) updateUserAdress");
		
		Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("javi");
		user.setActive(true);
		user.setName("Javier");		
		user.setSurname("Gonzalez");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Male);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("Jamon");
		user.addPreference("Cocina");
		user.addPreference("Paisajes");
		user.setEmail("Javier@prueba.com");
		user.setPass("javi");
		
		UserDao dao = new UserDaoImpl();
		dao.addUser(user);				
		assertEquals(user, session.get(User.class, user.getId()));	
		user.getAddress().setCity("Tudela");
		user.getAddress().setCountry("España");
		user.getAddress().setState("Navarra");
		
		user.getConfiguration().setShowName(false);
		user.getConfiguration().setShowPhone(true);
		user.getConfiguration().setShowEmail(true);
		
		dao.updateUser(user);
		
		assertEquals(user, session.get(User.class, user.getId()));
	
		}
		@Test
		public void addFriendTest(){
			this.logger.info("TEST(UserDao) addFriend");
			
			Address address = new Address();		
			address.setStreet("alcobendas 16");		
			address.setCity("Madrir");
			address.setState("Madrid");
			address.setCountry("Spain");
			
			Configuration conf = new Configuration();
											
			User user = new User();
			user.setNick("Raulete");
			user.setActive(true);
			user.setName("Raul");		
			user.setSurname("Macua");
			user.setPhone(944655877);
			user.setBirthdate(new Date());
			user.setGender(Gender.Male);
			user.setAddress(address);
			user.setConfiguration(conf);		
			user.addPreference("chorizo");
			user.addPreference("Cocina");
			user.addPreference("salsa");
			user.setEmail("Raul@prueba.com");
			user.setPass("raul");
			
			UserDao dao = new UserDaoImpl();
			dao.addUser(user);				
			assertEquals(user, session.get(User.class, user.getId()));
			
			System.out.println("El id del usuario es : ********************"+ user.getId());
			
			Address address2 = new Address();		
			address2.setStreet("vestigios 32");		
			address2.setCity("Vitoria");
			address2.setState("Alava");
			address2.setCountry("Spain");
			
			Configuration conf2 = new Configuration();
											
			User user2 = new User();
			user2.setNick("Maria");
			user2.setActive(true);
			user2.setName("Maria");		
			user2.setSurname("Subijana");
			user2.setPhone(944655877);
			user2.setBirthdate(new Date());
			user2.setGender(Gender.Female);
			user2.setAddress(address2);
			user2.setConfiguration(conf2);		
			user2.addPreference("salsa");
			user2.addPreference("bailes");
			user2.addPreference("Paisajes");
			user2.setEmail("Maria@prueba.com");
			user2.setPass("maria");
			
			
			dao.addUser(user2);				
			assertEquals(user2, session.get(User.class, user2.getId()));
			System.out.println("El id del usuario2 es : ********************"+ user2.getId());
			user.addFriend(user2);
			
			dao.updateUser(user);	
			assertEquals(user, session.get(User.class, user.getId()));
		}
	
		@Test
		public void findFriendTest(){
			
			//funciona , pero hay que cambiar el 115 , por un id de un usuario con amigos
		/*	this.logger.info("TEST(UserDao) findFriend");	
			UserDao dao = new UserDaoImpl();
			User user=dao.getUserById(115);
			List<User> listaAmigos=user.getFriends();
			System.out.print("el usuario con Id:"+ user.getId() +" tiene estos -");
			for(int i=0;i<listaAmigos.size();i++)
			{System.out.println("Amigos Id:"+listaAmigos.get(i).getId());}
		}*/
	
		}

}
