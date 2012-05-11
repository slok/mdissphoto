package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Embeddable;

import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;

@Embeddable
public class Address {
	
	 
	private String street;

	private String zip;	
	
	private short country;
	
	private short state;

	private int city;	
	
	public City getCity() {
		AddressDao dao = new AddressDaoImpl();
		City c=dao.getCity(this.city);
		return c;
	}

	public void setCity(City city) {
		this.city = city.getId();
	}

	public Country getCountry() {
		AddressDao dao = new AddressDaoImpl();
		Country c=dao.getCountry(this.country);
		return c;
	}

	public void setCountry(Country country) {
		this.country = country.getId();
	}

	public State getState() {
		AddressDao dao = new AddressDaoImpl();
		State s=dao.getState(this.state);
		return s;
	}

	public void setState(State state) {
		this.state = state.getId();
	}
	
	public Address(){}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}	

}
