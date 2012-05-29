package org.mdissjava.mdisscore.view.registration;

import java.util.Date;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.KeyLink;
import org.mdissjava.mdisscore.model.pojo.State;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.User.Gender;
import org.mdissjava.notifier.event.manager.NotificationManager;
import org.mdissjava.notifier.event.observable.VerifyAccountObservable;

import com.google.code.morphia.Datastore;

@ManagedBean
@RequestScoped
public class RegistrationBean {

	// Variables
	UserManager userBll = new UserManagerImpl();
	private String nick;
	private String name;
	private String surname;
	private Date birthdate;
	private Gender gender = Gender.Male;
	private String email;
	private String password;

	private static Logger logger = Logger.getLogger(RegistrationBean.class
			.getName());

	private boolean TermsAccepted = false;

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
	public RegistrationBean() {

	}

	public void setUserBll(UserManager userBll) {
		this.userBll = userBll;
	}

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public void setFavGender(String gender) {
		if (gender.equals("Male"))
			setGender(Gender.Male);
		else
			setGender(Gender.Female);
	}

	public String getFavGender() {
		if (getGender().equals(Gender.Male))
			return "Male";
		else
			return "Female";
	}

	public void register(ActionEvent actionEvent) throws ValidatorException {
		System.out.println("Register clicked*****************");
		City city = new City();
		city.setId(0);
		Country country = new Country();
		country.setId((short) 0);
		State state = new State();
		state.setId((short) 0);
		Address address = new Address();
		address.setCity(city);
		address.setCountry(country);
		address.setState(state);
		User user = new User();
		user.setActive(false);
		user.setNick(getNick());
		user.setName(getName());
		user.setSurname(getSurname());
		user.setPass(getPassword());
		user.setEmail(getEmail());
		user.setBirthdate(getBirthdate());
		user.setGender(getGender());
		user.setAddress(address);
		user.setConfiguration(new Configuration());
		user.setRole("user");
		userBll.saveUser(user);

		// Validation Key creation
		RegistrationBean.logger.info("Now keylink creation");
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		KeyLinkDao keylinkdao = new KeyLinkDaoImpl(db);
		KeyLink keylink = new KeyLink(user.getId(), KeyLink.EMAIL_VALIDATION);
		keylinkdao.insertKeyLink(keylink);
		RegistrationBean.logger.info("Created: " + keylink);
		// Notification
		NotificationManager notifier = NotificationManager.getInstance();
		VerifyAccountObservable vao = notifier.getVerifyAccountObservable();

		vao.userRegister(
				user.getName() + " " + user.getSurname(),
				user.getEmail(),
				"http://jboss.mdiss.info/mdissphoto/p/validate/"
						+ keylink.getId());
		RegistrationBean.logger.info("Registration notification sent");
		String outcome = "pretty:confirmation";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().getNavigationHandler()
				.handleNavigation(facesContext, null, outcome);

	}
	/*
	 * private ParamsBean getPrettyfacesParams() { FacesContext context =
	 * FacesContext.getCurrentInstance(); ParamsBean pb = (ParamsBean)
	 * context.getApplication().evaluateExpressionGet(context, "#{paramsBean}",
	 * ParamsBean.class); return pb; }
	 */

	/*
	 * public void handleDateSelect(DateSelectEvent event) { FacesContext
	 * facesContext= FacesContext.getCurrentInstance(); //SimpleDateFormat
	 * format= new SimpleDateFormat("d/M/yyyy"); Date aux=new Date();
	 * aux.setYear(aux.getYear()-18); if(event.getDate().compareTo(aux)>0) {
	 * facesContext.addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_WARN,
	 * "para utilizar la aplicacion debes ser mayor de edad o tener el consentimiento de tus padres"
	 * ,null)); year18old=false; } else year18old=true; }
	 */

}
