package org.mdissjava.mdisscore.view.registration;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;

@ManagedBean(name = "registrationbean")
@RequestScoped
public class RegistrationBean {

	UserManager userBll= new UserManagerImpl();
	User user=new User();
	
	
	public RegistrationBean()
	{
	}
	
	public void setUserBll(UserManager userBll) {
		this.userBll = userBll;
	}
	
	public String getNick() {
		return user.getNick();
	}
	public void setNick(String nick) {
		this.user.setNick(nick);
	}
	public String getName() {
		return user.getName();
	}
	public void setName(String name) {
		this.user.setName(name);
	}
	public String getSurname() {
		return user.getSurname();
	}
	public void setSurname(String surname) {
		this.user.setSurname(surname);
	}
	public Date getBirthdate() {
		return user.getBirthdate();
	}
	public void setBirthdate(Date birthdate) {
		this.user.setBirthdate(birthdate);
	}
	public int getPhone() {
		return user.getPhone();
	}
	public void setPhone(int phone) {
		this.user.setPhone(phone);
	}
	
	public List<String> getPreferences() {
		return user.getPreferences();
	}
	public void addPreference(String preferencia)
	{
		this.user.addPreference(preferencia);
	}
	public Gender getGender() {
		return user.getGender();
	}
	
	public void setGender(Gender gender) {
		this.user.setGender(gender);
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public void setEmail(String email) {
		this.user.setEmail(email);
	}
	
	public String getPassword() {
		return user.getPass();
	}
	
	public void setPassword(String pass) {
		this.user.setPass(pass);
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
		
		userBll.saveUser(this.user);
		
		return "";
	}
}
