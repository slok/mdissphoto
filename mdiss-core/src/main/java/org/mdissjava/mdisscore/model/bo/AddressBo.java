package org.mdissjava.mdisscore.model.bo;

import org.mdissjava.mdisscore.model.pojo.Address;

public class AddressBo {

	private String street;
	private String city;	
	private String zip;	
	private String country;
	private String state;
	
	
	AddressBo()
	{}
	
	AddressBo(Address address){
		this.street=address.getStreet();
		this.city=address.getCity();
		this.zip=address.getZip();
		this.country=address.getCountry();
		this.state=address.getState();
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	
	
	public boolean equals()
	{
		
		return true;
	}
	
	
}
