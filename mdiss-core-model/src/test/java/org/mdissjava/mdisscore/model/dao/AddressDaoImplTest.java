package org.mdissjava.mdisscore.model.dao;

import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class AddressDaoImplTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void findCountry(){
		
		this.logger.info("TEST(AddressDao) findCountry");	
		AddressDao dao = new AddressDaoImpl();
		Country c=dao.getCountry((short)100);
		
		System.out.println("Country: "+ c.getNombre());
	}
	
	@Test
	public void findState(){
		
		this.logger.info("TEST(AddressDao) findState");	
		AddressDao dao = new AddressDaoImpl();
		State state=dao.getState((short)50);
		
		System.out.println("State: "+ state.getNombre());
	}
	
	@Test
	public void findCity(){
		
		this.logger.info("TEST(AddressDao) findCity");	
		AddressDao dao = new AddressDaoImpl();
		City city=dao.getCity(986);
		
		System.out.println("City: "+ city.getNombre());
	}
	
	@Test
	public void findAllCountries(){
		this.logger.info("TEST(AddressDao) findAllCountries");	
		AddressDao dao = new AddressDaoImpl();
		List<Country> countries;
		countries=dao.getCountries();
		for(int i=0;i<countries.size();i++)
		{
			System.out.println("Pais "+i+" de la lista paises: "+countries.get(i).getNombre());
		}
	}
	
}
