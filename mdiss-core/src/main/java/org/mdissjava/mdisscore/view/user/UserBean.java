package org.mdissjava.mdisscore.view.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;

@RequestScoped
@ManagedBean
public class UserBean {
	
	private String userNick;
	private List<User> follows;
	private List<User> followers;
	
	private UserManager userManager;
	
	private User user;

	public UserBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.userNick = "user1";
		
		userManager = new UserManagerImpl();		
		this.user = userManager.getUserByNick(this.userNick);	
		follows = userManager.findFollows(user.getNick());
	}
		
	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
		
	public User getUser(){
		return this.user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public List<User> getFollows(){
		return this.follows;
	}
	
	public void setFollows(List<User> follows){
		this.follows = follows;
	}
	
	public List<User> getFollowers(){
		return this.followers;
	}
	
	public void setFollowers(List<User> followers){
		this.followers = followers;
	}
	
	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
	
	public void addFollow(String nick){
		
	}
	
	
}
