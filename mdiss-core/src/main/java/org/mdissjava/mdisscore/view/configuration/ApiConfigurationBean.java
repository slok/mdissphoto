package org.mdissjava.mdisscore.view.configuration;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.bll.UserHmacTokensManager;
import org.mdissjava.mdisscore.controller.bll.UserOauthTokensManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserHmacTokensManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.UserOauthTokensManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserOauthTokensDaoImpl;
import org.mdissjava.mdisscore.model.pojo.UserHmacTokens.HmacService;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class ApiConfigurationBean {
	
	
	private String twitterToken;
	private String mdissphotoToken;
	private Datastore datastore;
	
	private String username;
	private UserOauthTokensManager oauthManager;
	private UserHmacTokensManager hmacManager;
	
	public ApiConfigurationBean() {
		String database = "mdissphoto";
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
		this.username = this.getUser();
		
		//Twitter
		this.oauthManager = new UserOauthTokensManagerImpl(datastore);
		try{
			this.twitterToken = oauthManager.getUserOauthAccessToken(username, Service.TWITTER).getToken();
		}catch(IllegalAccessError iae)
		{
			this.twitterToken = "No access token, the next time you tweet will be setted";
		}catch (IllegalArgumentException iae) {
			this.twitterToken = "No access token, the next time you tweet will be setted";
		}
		
		//Mdissphoto
		this.hmacManager = new UserHmacTokensManagerImpl(datastore);
		try{
			this.mdissphotoToken = this.hmacManager.getUserHmacToken(username, HmacService.MDISSPHOTO);
		}catch(IllegalAccessError iae){
			this.mdissphotoToken = "No private key for the API. Generete a new one please";
		}catch (IllegalArgumentException iae) {
			this.mdissphotoToken = "No private key for the API. Generete a new one please";
		}
	}
	
	public void deleteTwitterToken(){
		try{
			this.oauthManager.deleteUserOauthAccessToken(username, Service.TWITTER);
			this.twitterToken = "No access token, the next time you tweet will be setted";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("deleted twitter token"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting Twitter token"));
		}
	}
	
	public void regenerateMdissPhotoToken(){
		try{
			this.mdissphotoToken = this.hmacManager.insertOrUpdateUserHmacToken(username, HmacService.MDISSPHOTO);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Generated new API key"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error generating new API key"));
		}
		
	}
	
	public String getTwitterToken() {
		return twitterToken;
	}
	public void setTwitterToken(String twitterToken) {
		this.twitterToken = twitterToken;
	}
	public String getMdissphotoToken() {
		return mdissphotoToken;
	}
	public void setMdissphotoToken(String mdissphotoToken) {
		this.mdissphotoToken = mdissphotoToken;
	}
	
	public String getUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	

}
