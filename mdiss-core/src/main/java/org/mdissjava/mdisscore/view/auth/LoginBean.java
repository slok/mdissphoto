package org.mdissjava.mdisscore.view.auth;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

@RequestScoped
@ManagedBean
public class LoginBean {
	
	private String username;
	private String password;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String doLogin() throws ServletException, IOException
	{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check?j_username=" + this.username + "&j_password=" + this.password);
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();  
        
     // It's OK to return null here because Faces is just going to exit.  
        return null;
	}
	
	public String setLoginError(){
		
		//Authentication exception check to show message errors
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		
		//Access the messages.properties file (not the _es)
		String messageBundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
		ResourceBundle messageBundle = ResourceBundle.getBundle(messageBundleName);
		
		//If Spring throws an Authentication (BadCredentialsException) error, an error message is created to be shown
		if(session != null)
		{
    		Exception ex = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    		if (ex instanceof BadCredentialsException)
	        {
	            FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("loginError"), messageBundle.getString("badCredentials")));
	            this.logger.error("AUTHENTICATION ERROR: {}", ex.getMessage());
	        }
	        else if (ex instanceof AccountStatusException)
	        {
	        	FacesContext.getCurrentInstance().addMessage(
	                    null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("loginError"), messageBundle.getString("accountDisabled")));
	            this.logger.error("AUTHENTICATION ERROR: {}", ex.getMessage());
	        }
    		session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
		}

		return null;
	}

}
