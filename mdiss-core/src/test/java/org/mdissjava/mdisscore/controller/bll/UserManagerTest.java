package org.mdissjava.mdisscore.controller.bll;


import java.util.Date;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagerTest {


	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void setUp() throws Exception {
		HibernateUtil.getSession();
	}
	
	
	private UserManager userManager=new UserManagerImpl();
	private static int idUsuario;
	
	@Test
	public void addUserBoTest() throws Exception{		
		this.logger.info("TEST(UserDao) addUser");
	
		Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("Juan");
		user.setActive(true);
		user.setName("Juanjo");		
		user.setSurname("Guerra");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Male);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		user.setEmail("prueba@prueba.com");
		user.setPass("1234");

		userManager.saveUser(user);
		
		if(user.getId()==0)
			throw new Exception("El objeto guardado no a recibido correctamente el nuevo id de almacenaje");
		else
			idUsuario=user.getId();
	}
	
	@Test
	public void UpdateUserBoTest() throws Exception{
		
		User user=userManager.getUserById(idUsuario);
		if(user==null)
			throw new Exception("El usuario es nulo");
		else
		{
			user.setSurname("Abradacurcis");
			userManager.saveUser(user);
		}
		
	}
	
	@Test
	public void DeleteUserBoTest() throws Exception{
		
		User user=userManager.getUserById(idUsuario);
		if(user==null)
			throw new Exception("El usuario es nulo");
		else
		{
			userManager.deleteUser(user);			
		}
		
		
	}
	
	
}
