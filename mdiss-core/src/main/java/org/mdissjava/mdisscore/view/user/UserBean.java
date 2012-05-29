package org.mdissjava.mdisscore.view.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequestScoped
@ManagedBean
public class UserBean {
	
	private String userNickname;
	private List<User> follows;
	private List<User> followers;
	
	private UserManager userManager;
	
	private User user;
	
	private int page;
	
	private String userId;

	public UserBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.page = pb.getPage();
		if (this.page == 0){
			this.page = 1;
		}
		this.userId = pb.getUserId();
		this.userManager = new UserManagerImpl();			
		this.userNickname = retrieveSessionUserNick();	
		this.user = userManager.getUserByNick(this.userId);			
	}
		
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
		
	public User getUser(){
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<User> getFollows() {
		return this.follows;
	}
	
	public void setFollows(List<User> follows) {
		this.follows = follows;
	}
	
	public List<User> getFollowers() {
		this.followers = userManager.findFollowers(user.getNick(), page);
		return this.followers;
	}
	
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	public void addFollow(User follow) {		
		userManager.addFollow(this.userNickname, follow);
	}
	
	public void deleteFollow(User follow) {		
		userManager.deleteFollow(this.userNickname, follow);
	}
	
	public boolean followsUser(User follow) {
		return userManager.followsUser(this.userNickname, follow);
	}
			
	private String retrieveSessionUserNick() {
	  //Get the current logged user's username
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  return auth.getName();		  
	}
	
	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
	
	
}
