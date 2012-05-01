package org.mdissjava.mdisscore.view.registration;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "registrationbean")
@ViewScoped
public class RegistrationBean {

	//Variables
	UserManager userBll= new UserManagerImpl();
	private String nick;
	private String name;
	private String surname;
	private Date birthdate;
	private Gender gender=Gender.Male;
	private String email;
	private String password;
	
	//wizard
	private static Logger logger = Logger.getLogger(RegistrationBean.class.getName());


 private boolean TermsAccepted=false;
	 
	 
	public boolean isTermsAccepted() {
			return this.TermsAccepted;
		}

	public void setTermsAccepted(boolean termsAccepted) {
			this.TermsAccepted = termsAccepted;
		}

	/**
	 * Manager for user variables 
	 * 
	 * 
	 * @author inigorst21
	 *
	 */	
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
		if(gender.equals("Male"))
			setGender(Gender.Male);
		else
			setGender(Gender.Female);
	}
	public String getFavGender()
	{
		if(getGender().equals(Gender.Male))
			return "Male";
		else
			return "Female";
	}
	
	public void register(ActionEvent actionEvent) throws ValidatorException  
	{ 	
		System.out.println("Register clicked*****************");
		

				User user=new User();
				user.setActive(false);
				user.setNick(getNick());
				user.setName(getName());
				user.setSurname(getSurname());
				user.setPass(getPassword());
				user.setEmail(getEmail());
				user.setBirthdate(getBirthdate());
				user.setGender(getGender());
	
				userBll.saveUser(user);
				

	}
	

/*	public void handleDateSelect(DateSelectEvent event)
	{
		FacesContext facesContext= FacesContext.getCurrentInstance();
		//SimpleDateFormat format= new SimpleDateFormat("d/M/yyyy");
		Date aux=new Date();
		aux.setYear(aux.getYear()-18);
		if(event.getDate().compareTo(aux)>0)
		{
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"para utilizar la aplicacion debes ser mayor de edad o tener el consentimiento de tus padres",null));
			year18old=false;
		}
		else
			year18old=true;
	}*/

}

