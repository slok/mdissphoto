package org.mdissjava.mdisscore.view.user;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@RequestScoped
@ManagedBean
public class DashboardBean {
	private String user;
	private String user2;

	//method 1 for getting the user from the session
	public String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName(); //get logged in username
	}
	
	//method 2 for getting the user grom the session
	public String getUser2() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername(); //get logged in username
	}
	

	public void setUser(String user) {
		this.user = user;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	
	

}
