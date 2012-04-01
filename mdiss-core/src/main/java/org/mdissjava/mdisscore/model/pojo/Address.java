package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Embeddable;

import org.mdissjava.mdisscore.model.bo.AddressBo;

@Embeddable
public class Address {
	
	private String street;
	private String city;	
	private String zip;	
	private String country;
	private String state;
	
	public Address(){}
	
	public Address(AddressBo Add)
	{
		this.street=Add.getStreet();
		this.city=Add.getCity();
		this.country=Add.getCountry();
		this.zip=Add.getZip();
		this.state=Add.getState();
	}
	
	public void setAddressBoData(AddressBo Add)
	{
		this.street=Add.getStreet();
		this.city=Add.getCity();
		this.country=Add.getCountry();
		this.zip=Add.getZip();
		this.state=Add.getState();
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

}
