package org.mdissjava.mdisscore.view.configuration;

import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RequestScoped
@ManagedBean
public class PerConfiguration {
	
	private String oldPassword;
	private String newPassword;
	
	private String nick;
	private String name;
	private String surname;
	private Gender gender;
	private Date birthdate;	
	private int phone;
	private String preferences;
	private String email;
	
	private String userNick;
	private User user;
	private UserManager userManager;
	
	
	public PerConfiguration()
	{
		this.userNick = retrieveSessionUserNick();
		
		userManager = new UserManagerImpl();		
		this.user = userManager.getUserByNick(this.userNick);
		this.nick=this.userNick ;
		this.setName(this.user.getName());
		this.email=this.user.getEmail();
		this.setGender(this.user.getGender());
		this.setPhone(this.user.getPhone());
		this.setSurname(this.user.getSurname());
		this.setBirthdate(this.user.getBirthdate());
		
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNick() {
		return nick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getPreferences() {
		return preferences;
	}
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
	public String getEmail() {
		return email;
	}

	
	private String retrieveSessionUserNick() {
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  return auth.getName();   
		 }
	
	public String doPerSave() throws ServletException, IOException
	{
		//System.out.println("Save clicked....");
		this.user.setName(this.getName());
		this.user.setSurname(this.getSurname());
		this.user.setBirthdate(this.getBirthdate());
		this.user.setPhone(this.getPhone());
		this.user.setGender(this.getGender());
		
		userManager.saveUser(this.user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Personal settings updated"));
		return null;
	}
	
	
	public String doPasswordSave() throws ServletException, IOException
	{
		System.out.println("Save password clicked....");
		if(userManager.changePassword(this.user, this.getOldPassword(), this.getNewPassword()))
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password changed"));			
		}
		else
		{
			FacesMessage msg	=new FacesMessage("Password change Failed.", 
					"Please enter correct password.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null , msg);
					
		
		}
		return null;
	}

}
