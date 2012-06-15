package org.mdissjava.mdisscore.view.configuration;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean
@ViewScoped
public class SecConfiguration{
	
	private User user;
	private String userNick;
	private UserManager userManager;
	private boolean disabled;
	
	public SecConfiguration()
	{
		this.userNick = retrieveSessionUserNick();	
		userManager = new UserManagerImpl();		
		this.setUser(userManager.getUserByNick(this.userNick));	

		if(this.user.getConfiguration().isIsPrivate())
		{
			this.disabled = true;
		}
		else
		{
			this.disabled = false;
		}
	}

	private String retrieveSessionUserNick() {
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  return auth.getName();	   
		 }


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public void disableCheckboxes(AjaxBehaviorEvent event) {
		if(this.disabled == true)
		{
			this.disabled = false;
		}
		else
		{
			this.disabled = true;
		}
	}

	public String doSecuritySave() throws ServletException, IOException
	{
		userManager.saveUser(this.user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Security settings updated"));
		return null;
	}

}
