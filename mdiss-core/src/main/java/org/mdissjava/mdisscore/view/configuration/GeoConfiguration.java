package org.mdissjava.mdisscore.view.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;

@ManagedBean
@RequestScoped
public class GeoConfiguration implements Serializable{
	
	private AddressDao adBll=new AddressDaoImpl(); 
	
    private String country;  
  
    private String state;  
      
    private Map<String,String> countries = new HashMap<String, String>();  
  
    private Map<String,Map<String,String>> statesData = new HashMap<String, Map<String,String>>();  
      
    private Map<String,String> states = new HashMap<String, String>();  
  
    public GeoConfiguration() { 
    	
    	List<Country>Lpaises= adBll.getCountries();	
    	for(int i=0;i<Lpaises.size();i++)
    		countries.put(Lpaises.get(i).getNombre(),Short.toString(Lpaises.get(i).getId()));
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
  
    public Map<String, String> getCountries() {  
        return countries;  
    }  
  
    public void setCountries(Map<String, String> countries) {  
        this.countries = countries;  
    }  
      
    public Map<String, Map<String, String>> getStatesData() {  
        return statesData;  
    }  
  
    public void setStatesData(Map<String, Map<String, String>> statesData) {  
        this.statesData = statesData;  
    }  
      
    public Map<String, String> getStates() {  
        return states;  
    }  
  
    public void setSuburbs(Map<String, String> states) {  
        this.states = states;  
    }  
      
    public void handleCountryChange() {  
        if(country !=null && !country.equals(""))
        {
        	System.out.println("Country clicked..."+country);
        	List<State>Lestados= adBll.getStates(Short.parseShort(country));
        	for(int i=0;i<Lestados.size();i++)
        		{
        		System.out.println("Added to the List"+Lestados.get(i).getNombre());
        		states.put(Lestados.get(i).getNombre(),Short.toString(Lestados.get(i).getId()));
        		}
        	
        }   
        else  
            states = new HashMap<String, String>();  
    }  
  
    public void displayLocation() {  
        FacesMessage msg = new FacesMessage("Selected", "Country:" + country + ", State: " + state);  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
}  


