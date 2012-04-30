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
	private int phone ;
	private List<String> preferences;
	private Gender gender=Gender.Male;
	private String email;
	private String password;
	private String street;
	private String city;	
	private String zip;	
	private String state;
	private String country;
	
	//wizard
	private boolean skip;
	private static Logger logger = Logger.getLogger(RegistrationBean.class.getName());
	private int BirthdayDay=0;
	private int BirthdayMonth=0;
	private int BirthdayYear=0;
	static int ActYear=new Date().getYear()+1900;
	//private Date today=new Date();
	boolean year18old=true;
	private boolean TermsAccepted;
	/**
	 * Manager for wizard
	 * 
	 * 
	 * @author inigorst21
	 *
	 */	
    public boolean isSkip() {  
        return skip;  
    }  
  
    public void setSkip(boolean skip) {  
        this.skip = skip;  
    }  
	
	/*public Date getToday()
	{
		return this.today;
	}*/
	
	 public String onFlowProcess(FlowEvent event) {  
	        logger.info("Current wizard step:" + event.getOldStep());  
	        logger.info("Next step:" + event.getNewStep());  
	          
	        if(skip) {  
	            skip = false;   //reset in case user goes back  
	            return "confirm";  
	        }  
	        else {  
	            return event.getNewStep();  
	        }  
	    }  
	 //DIAS
	 public static List<Integer> BirthdayDayValues;
	 static{
		 BirthdayDayValues = new LinkedList<Integer>();
		 for(int i=1;i<32;i++)
		 BirthdayDayValues.add( i); //label, value
	}
	 
	 public List<Integer> getFavBirthdayDayValue()
	 {
		 return BirthdayDayValues;
	}

	 public int getFavBirthdayDay()
	 {
		 return this.BirthdayDay;
	 }
	 public void setFavBirthdayDay(int day)
	 {
		 this.BirthdayDay=day;
	 }
	 
	 //MES
	 public static Map<String,Object> BirthdayMonthValues;
	 static{
		 BirthdayMonthValues = new LinkedHashMap<String,Object>();
		 BirthdayMonthValues.put("Enero", 1); //label, value
		 BirthdayMonthValues.put("Febrero", 2);
		 BirthdayMonthValues.put("Marzo", 3);
		 BirthdayMonthValues.put("Abril", 4);
		 BirthdayMonthValues.put("Mayo", 5);
		 BirthdayMonthValues.put("Junio", 6);
		 BirthdayMonthValues.put("Julio", 7);
		 BirthdayMonthValues.put("Agosto", 8);
		 BirthdayMonthValues.put("Septiembre", 9);
		 BirthdayMonthValues.put("Octubre", 10);
		 BirthdayMonthValues.put("Noviembre", 11);
		 BirthdayMonthValues.put("Diciembre", 12);
	}
	 
	 public Map<String,Object> getFavBirthdayMonthValue()
	 {
		 return BirthdayMonthValues;
	}

	 public int getFavBirthdayMonth()
	 {
		 return this.BirthdayMonth;
	 }
	 public void setFavBirthdayMOnth(int month)
	 {
		 this.BirthdayMonth=month;
		
	 }
	 
	 //AÑos
	 public static Map<String,Object> BirthdayYearValues;
	 static{
		 BirthdayYearValues = new LinkedHashMap<String,Object>();
		 for(int e=ActYear;e>ActYear-125;e--)
		 BirthdayYearValues.put(String.valueOf(e), e); //label, value
	}
	 
	 public Map<String,Object> getFavBirthdayYearValue()
	 {
		 return BirthdayYearValues;
	}

	 public int getFavBirthdayYear()
	 {
		 return this.BirthdayYear;
	 }
	 public void setFavBirthdayYear(int year)
	 {
		 this.birthdate.setYear(year);
		 this.BirthdayYear=year;
	 }
	 
	 
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
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void register(ActionEvent actionEvent) throws ValidatorException  
	{ 	
		System.out.println("Register clicked*****************");
		
			//	Configuration conf = new Configuration();
			//	Address dir= new Address();
				User user=new User();
				user.setActive(false);
				user.setNick(getNick());
				user.setName(getName());
				user.setSurname(getSurname());
				user.setPass(getPassword());
				user.setEmail(getEmail());
				user.setPhone(getPhone());
				user.setGender(getGender());
			//	user.setBirthdate(getBirthdate());
			//	user.setConfiguration(conf);
			//	user.setAddress(dir);
		
		
				userBll.saveUser(user);
				

	}
	
	/*	public void submitValidation(FacesContext context, UIComponent component,
	        Object value)throws ValidatorException 
	{
		if(userBll.EmailAllReadyExist(this.getEmail()))
		{
			throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Not valid",
                "Email ya existe"));
		}
		else
		{
			if(userBll.NickAllReadyExist(this.getNick()))
			{
				throw new ValidatorException(new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, "Not valid",
	                "Nick ya existe"));
			}
		}
	}*/
		

	
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

