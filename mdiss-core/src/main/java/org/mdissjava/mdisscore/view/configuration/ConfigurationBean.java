package org.mdissjava.mdisscore.view.configuration;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.controller.bll.AddressManager;
import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.AddressManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;
import org.mdissjava.mdisscore.model.pojo.User.Gender;


@ManagedBean
@RequestScoped
public class ConfigurationBean {

	AddressManager addBll = new AddressManagerImpl();
	UserManager userBll= new UserManagerImpl();
	private String nick;
	private String name;
	private String surname;
	private Date birthdate;
	private int phone ;
	private List<String> preferences;
	private Gender gender=Gender.Male;
	private String email;
	private String password;
	private String street;
	private City city;	
	private String zip;	
	private State state;
	private Country country;
	
	
	/**
	 * Functions of configuation
	 * 
	 * @author inigorst21
	 */
	
	
	public void setUserBll(UserManager userBll) {
		this.userBll = userBll;
	}
	
	public String getNick() {
		return this.nick;
	}
	public void setNick(String nick) {
		this.nick=nick;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(String surname) {
		this.surname=surname;
	}
	public Date getBirthdate() {
		return this.birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate=birthdate;
	}
	public int getPhone() {
		return this.phone;
	}
	public void setPhone(int phone) {
		this.phone=phone;
	}
	
	public String getIndexPreference(int index)
	{
		return this.preferences.get(index);
	}
	public List<String> getPreferences() {
		return this.preferences;
	}
	public void addPreference(String preferencia)
	{
		this.preferences.add(preferencia);
	}
	public Gender getGender() {
		return this.gender;
	}
	
	public void setGender(Gender gender) {
		this.gender=gender;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {		
			this.email=email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String pass) {
		this.password=pass;
	}
	
	public void setFavGender(String gender)
	{
		if(gender.equals("Hombre"))
			setGender(Gender.Male);
		else
			setGender(Gender.Female);
	}
	public String getFavGender()
	{
		if(getGender().equals(Gender.Male))
			return "Hombre";
		else
			return "Mujer";
	}
	
	public List<Country> getCountryList()
	{
		return addBll.getAllCountries();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	

	
	
	
}
