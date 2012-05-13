package org.mdissjava.mdisscore.controller.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.controller.bll.AddressManager;
import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;

public class AddressManagerImpl implements AddressManager {

	private AddressDao addao=new AddressDaoImpl();
	
	@Override
	public List<Country> getAllCountries() {
		// TODO Get all countries logical layer function
		return addao.getCountries();	
	}

	@Override
	public List<State> getStateList(short idCountry) {
		// TODO Get all States logical layer function
		return addao.getStates(idCountry);
	}

	@Override
	public List<City> getCityList(short idCountry, short idState) {
		// TODO get all cities from a country and state business logical layer function
		return addao.getCities(idCountry, idState);
	}

}
