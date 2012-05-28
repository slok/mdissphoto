package org.mdissjava.mdisscore.view.configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;
import org.mdissjava.mdisscore.model.pojo.User;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
@ViewScoped
public class GeoConfiguration implements Serializable{
	
	private AddressDao adBll=new AddressDaoImpl(); 
	
	private UserManager userManager;
	
	private String userNick;
	
	private User user;
	
    private String country="0";  
  
    private String state="0";
    
    private String city="0";
    
    private String zip;
    
    private String street;
    
    private float coordx=(float) 39.095963;
    
    private float coordy=(float) -99.067385;
    
    private int zoom=5;
      
    private SortedMap<String, String> countries = new TreeMap<String, String>();    
      
    private SortedMap<String, String> states = new TreeMap<String, String>();
    
    private SortedMap<String, String> cities = new TreeMap<String, String>();
  
    public GeoConfiguration() { 
    	
    	this.userNick = retrieveSessionUserNick();
		
		userManager = new UserManagerImpl();		
		this.user = userManager.getUserByNick(this.userNick);
	  	
    	List<Country>Lpaises= adBll.getCountries();	
    	for(int i=0;i<Lpaises.size();i++)
    		countries.put(Lpaises.get(i).getNombre(),Short.toString(Lpaises.get(i).getId()));
    	
    	if(user.getAddress().getCountryId()!=0)
    	{
    		this.setCountry(String.valueOf(user.getAddress().getCountryId()));
    		List<State>Lstate = adBll.getStates(user.getAddress().getCountryId());
    		for(int i=0;i<Lstate.size();i++)
        		states.put(Lstate.get(i).getNombre(),Short.toString(Lstate.get(i).getId()));
    		List<City>Lcities = adBll.getCities(user.getAddress().getCountryId(),user.getAddress().getState().getId());
    		for(int i=0;i<Lcities.size();i++)
        		cities.put(Lcities.get(i).getNombre(),Integer.toString(Lcities.get(i).getId()));
    		this.setState(String.valueOf(user.getAddress().getState().getId()));
    		this.setCity(String.valueOf(user.getAddress().getCity().getId()));
    		this.setStreet(user.getAddress().getStreet());
    		this.setZip(user.getAddress().getZip());
    		this.setCoordenadasXY(user.getAddress().getCity().getX(), user.getAddress().getCity().getY());
    		this.zoom=13;
    	}
    	
    }  
      
    public String getCountry() {  
        return country;  
    }  
  
    public void setCountry(String country) {  
        this.country = country;  
    }  
    
	public Country getCountryObject()
	{
		if(!country.equals(""))
			return adBll.getCountry(Short.valueOf(this.country));
		else
			return null;
	}
  
    public SortedMap<String, String> getCountries() {  
        return countries;  
    }  
  
    public void setCountries(SortedMap<String, String> countries) {  
        this.countries = countries;  
    }        
      
    public String getState() {  
        return state;  
    }  
  
    public void setState(String state) {  
        this.state = state;  
    } 
    
	public State getStateObject()
	{
		if(!state.equals(""))
			return adBll.getState(Short.valueOf(this.state));
		else
			return null;
	}
    
    public SortedMap<String, String> getStates() {  
        return states;  
    }  
  
    public void setStates(SortedMap<String, String> states) {  
        this.states = states;  
    }
    

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	} 
	
	public City getCityObject()
	{
		if(!city.equals(""))
			return adBll.getCity(Integer.valueOf(this.city));
		else
			return null;
	}
	
	public SortedMap<String, String> getCities() {
		return cities;
	}

	public void setCities(SortedMap<String, String> cities) {
		this.cities = cities;
	}
   
	
    public void AjaxStateChange() {  
        if(state !=null && !state.equals(""))
        { 
        	System.out.println("State clicked..."+state);
        	//Introduzco las coordenadas seleccionadas
        	State s=this.getStateObject();
        	if(s!=null)
        		this.setCoordenadasXY(s.getX(), s.getY());
        	zoom=10;
        	cities = new TreeMap<String, String>();
        	List<City>Lciudades= adBll.getCities(Short.parseShort(country), Short.parseShort(state));
        	for(int i=0;i<Lciudades.size();i++)
        		{
        		System.out.println("Added to the List: "+Lciudades.get(i).getNombre());
        		cities.put(Lciudades.get(i).getNombre(),Integer.toString(Lciudades.get(i).getId()));
        		}
        }   
        else  
        {    
        	cities =new TreeMap<String, String>();
        	this.setCity("0");
        }
    }
	
	
    public void AjaxCountryChange() {  
        if(country !=null && !country.equals(""))
        {
        	System.out.println("Country clicked..."+country);
        	//Coordenadas del pais
        	Country c=this.getCountryObject();
        	if(c!=null)
        		this.setCoordenadasXY(c.getX(), c.getY());
        	zoom=6;
        	cities = new TreeMap<String, String>();
        	states = new TreeMap<String, String>();
        	List<State>Lestados= adBll.getStates(Short.parseShort(country));
        	for(int i=0;i<Lestados.size();i++)
        		{
        		System.out.println("Added to the List: "+Lestados.get(i).getNombre());
        		states.put(Lestados.get(i).getNombre(),Short.toString(Lestados.get(i).getId()));
        		}
        }   
        else  
        {    
        	states = new TreeMap<String, String>();
        	cities = new TreeMap<String, String>();
        	this.setState("0");
        	this.setCity("0");
        }
    } 
    
    public void AjaxCityChange()
    {
    	City c=this.getCityObject();
    	if(c!=null)
    		this.setCoordenadasXY(c.getX(), c.getY());
    	zoom=13;
    	System.out.println("Ciudad: "+c.getNombre()+" seleccionada con coordenadas: "+this.getCoordx()+" , "+this.getCoordy());
    }
    

  
    public void displayLocation() {  
        FacesMessage msg = new FacesMessage("Selected", "Country:" + country + ", State: " + state +" City: "+ city);  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    private void setCoordenadasXY(float x, float y)
    {
    	this.setCoordx(x);
    	this.setCoordy(y);
    }
    
    public String getCoordenadasXY()
    {
    	return this.getCoordx()+","+this.getCoordy();
    }
    
    
    
    //Coordenadas
	private float getCoordx() {
		return coordx;
	}

	private void setCoordx(float coordx) {
		this.coordx = coordx;
	}

	private float getCoordy() {
		return coordy;
	}

	private void setCoordy(float coordy) {
		this.coordy = coordy;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}
	
	public MapModel getSimpleModel() {
		MapModel simpleModel = new DefaultMapModel();
		LatLng coord1 = new LatLng(this.getCoordx(), this.getCoordy());
		simpleModel.addOverlay(new Marker(coord1, "Your location"));  
		return simpleModel;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String doSave() throws ServletException, IOException
	{
		//System.out.println("Valores de cidauda, stado y pais: "+this.getCity()+" , "+ this.getState() +" , "+this.getCountry());
		if(!this.getCountry().equals("0")&& !this.getState().equals("0") && !this.getCity().equals("0"))
		{
		this.user.getAddress().setCountry(this.getCountryObject());
		this.user.getAddress().setState(this.getStateObject());
		this.user.getAddress().setCity(this.getCityObject());
		this.user.getAddress().setStreet(this.getStreet());
		this.user.getAddress().setZip(this.getZip());
		
		this.userManager.saveUser(this.user);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Geographical settings updated"));
		}
		else
		{
			FacesMessage msg	=new FacesMessage("Location Save Failed.", 
					"Please enter a valid City, State and Country.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null , msg);
					
		
		}
		return null;
	
	}

	private String retrieveSessionUserNick() {
		  //Get the current logged user's username
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  return auth.getName();
		   
		 }
 
}  


