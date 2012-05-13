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
	public List<State> getStateList(Country country) {
		// TODO Get all States logical layer function
		System.out.println("Address Business logial layer was call sucess...");
		return addao.getStates(country.getId());
	}

	@Override
	public List<City> getCityList(Country country, State state) {
		// TODO get all cities from a country and state business logical layer function
		return addao.getCities(country.getId(), state.getId());
	}

}
