package org.mdissjava.mdisscore.view.registration;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;

@ManagedBean(name = "registrationbean")
@ViewScoped
public class RegistrationBean {

	UserManager userBll= new UserManagerImpl();
	private String nick;
	private String name;
	private String surname;
	private Date birthdate;
	private int phone ;
	private List<String> preferences;
	private Gender gender;
	private String email;
	private String password;
	private String city;
	
	public RegistrationBean()
	{
	}
	
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
	
/*	public void setCity(String city){
		this.userBo.getAddress().setCity(city);
	}
	
	public String getCity(){
		return this.userBo.getAddress().getCity();
	}
	
	public void setStreet(String street)
	{
		this.userBo.getAddress().setStreet(street);
	}
	public String getStreet()
	{
		return this.userBo.getAddress().getStreet();
	}*/
	
	
	public String register()
	{ 	
		User user=new User();
		user.setActive(false);
		user.setNick(getNick());
		user.setName(getName());
		user.setSurname(getSurname());
		user.setPass(getPassword());
		user.setEmail(getEmail());
		user.setPhone(getPhone());
		
		
	/*	Address address = new Address();		
		address.setStreet("Madariaga 6");		
		address.setCity("Bilbao");
		address.setState("Vizcaya");
		address.setCountry("Spain");
		
		Configuration conf = new Configuration();
										
		User user = new User();
		user.setNick("Palomo");
		user.setActive(true);
		user.setName("Uriarte");		
		user.setSurname("Fermino");
		user.setPhone(944655877);
		user.setBirthdate(new Date());
		user.setGender(Gender.Male);
		user.setAddress(address);
		user.setConfiguration(conf);		
		user.addPreference("nature");
		user.addPreference("horses");
		user.addPreference("sunsets");
		user.setEmail("Palomo@iturnia.com");
		user.setPass("1234");*/
		
		
		userBll.saveUser(user);		
		return "";
	}
}
