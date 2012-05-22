package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;


public interface AddressDao {

	List<Country> getCountries();
	List<State> getStates(short idCountry);
	List<City> getCities(short idCountry,short idState);
	
	Country getCountry(short idCountry);
	State getState(short idState);
	City getCity(int idCity);
	
}
