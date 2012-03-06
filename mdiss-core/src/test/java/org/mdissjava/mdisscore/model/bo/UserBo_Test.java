package org.mdissjava.mdisscore.model.bo;

import java.util.Date;


import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;


public class UserBo_Test {

	//Test insercion de 1 nuevo usuario
	@Test
	public void test1()
	{
		
		HibernateUtil.openSessionFactory();
		
		
	UserBo user= new UserBo();
	ConfigurationBo conf = new ConfigurationBo();
	AddressBo dir= new AddressBo();
	
	conf.setShowSurname(false);
	
	dir.setCity("Madrid");
	dir.setCountry("España");
	dir.setStreet("Chiguavallo 27");
	dir.setState("Madrid");
	dir.setZip("92099");
	
	user.setNick("Juan2012");
	user.setName("Juan");		
	user.setSurname("Gonzales");
	user.setPhone(94899920);
	user.setBirthdate(new Date());
	user.setGender(Gender.Male);
	user.setAddress(dir);
	user.setConfiguration(conf);		
	user.addPreference("Perros");
	user.addPreference("gatos");
	user.addPreference("coches");
	user.setEmail("susan@prueba.com");
	user.setPass("prueba");
	user.Save();
	System.out.println(" Codigo generado "+user.getId());
	
	}
	
	
}
