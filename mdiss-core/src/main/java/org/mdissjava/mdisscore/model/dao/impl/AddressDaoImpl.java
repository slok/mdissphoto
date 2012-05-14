package org.mdissjava.mdisscore.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;

public class AddressDaoImpl implements AddressDao {


	@Override
	public State getState(short idState) {
		// TODO get State by idState
		State state=null;
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("" + "from State where id =" + idState + "");
		state = (State) q.uniqueResult();
		return state;
	}

	@Override
	public List<Country> getCountries() {
		// TODO get all countries
		List<Country> countries = new ArrayList<Country>();
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("from Country order by nombre");
		countries= q.list();
		return countries;
	}

	@Override
	public List<State> getStates(short idCountry) {
		// TODO get all states of a country
		System.out.println("Get States Dao");
		List<State> states = new ArrayList<State>();
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("from State where id_pais =" + idCountry+ "");
		states= q.list();
		for(int i=0 ; i<states.size();i++)
		{System.out.println("Estados recibidos:"+states.get(i).getNombre() );}
		return states;
	}

	@Override
	public List<City> getCities(short idCountry, short idState) {
		// TODO get all locations of a country and state
		List<City> cities = new ArrayList<City>();
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("from City where id_pais =" + idCountry+ " and id_region ="+idState);
		cities= q.list();
		return cities;
	}

	@Override
	public Country getCountry(short idCountry) {
		// TODO get Country by idState
				Country c=null;
				Session session = HibernateUtil.getSession();
				Query q = session.createQuery("" + "from Country where id =" + idCountry);
				c = (Country) q.uniqueResult();
				return c;
	}

	@Override
	public City getCity(int idCity) {
		// TODO get City by idCity
		City city=null;
		Session session = HibernateUtil.getSession();
		Query q = session.createQuery("" + "from City where id =" + idCity + "");
		city = (City) q.uniqueResult();
		return city;
	}


}
