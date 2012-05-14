package org.mdissjava.mdisscore.view.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.model.dao.AddressDao;
import org.mdissjava.mdisscore.model.dao.impl.AddressDaoImpl;
import org.mdissjava.mdisscore.model.pojo.City;
import org.mdissjava.mdisscore.model.pojo.Country;
import org.mdissjava.mdisscore.model.pojo.State;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@ViewScoped
public class GeoConfiguration implements Serializable{
	
	private AddressDao adBll=new AddressDaoImpl(); 
	
    private String country;  
  
    private String state;
    
    private String city;
    
    private float coordx=(float) 39.095963;
    
    private float coordy=(float) -99.067385;
    
    private int zoom=5;
      
    private Map<String,String> countries = new HashMap<String, String>();    
      
    private Map<String,String> states = new HashMap<String, String>();
    
    private Map<String,String> cities = new HashMap<String, String>();
  
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
    
	public Country getCountryObject()
	{
		if(!country.equals(""))
			return adBll.getCountry(Short.valueOf(this.country));
		else
			return null;
	}
  
    public Map<String, String> getCountries() {  
        return countries;  
    }  
  
    public void setCountries(Map<String, String> countries) {  
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
    
    public Map<String, String> getStates() {  
        return states;  
    }  
  
    public void setStates(Map<String, String> states) {  
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
	
	public Map<String,String> getCities() {
		return cities;
	}

	public void setCities(Map<String,String> cities) {
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
        	cities = new HashMap<String, String>();
        	List<City>Lciudades= adBll.getCities(Short.parseShort(country), Short.parseShort(state));
        	for(int i=0;i<Lciudades.size();i++)
        		{
        		System.out.println("Added to the List: "+Lciudades.get(i).getNombre());
        		cities.put(Lciudades.get(i).getNombre(),Integer.toString(Lciudades.get(i).getId()));
        		}
        }   
        else  
        {    
        	cities = new HashMap<String, String>();
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
        	cities = new HashMap<String, String>();
        	states = new HashMap<String, String>();
        	List<State>Lestados= adBll.getStates(Short.parseShort(country));
        	for(int i=0;i<Lestados.size();i++)
        		{
        		System.out.println("Added to the List: "+Lestados.get(i).getNombre());
        		states.put(Lestados.get(i).getNombre(),Short.toString(Lestados.get(i).getId()));
        		}
        }   
        else  
        {    
        	states = new HashMap<String, String>();
        	cities = new HashMap<String, String>();
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
	


 
}  


