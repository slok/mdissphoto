package org.mdissjava.mdisscore.view.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class UserBean {
	
	private String userNickname;
	private String userId;

	private List<User> follows;
	private List<User> followers;
	private List<String> photoURLs;
	
	private UserManager userManager;	
	private PhotoManager photoManager;
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
		
	private User user;	
	private int page;
	
	PropertiesFacade propertiesFacade;
	String database;
	
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
		
		try {
			propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);	
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			photoManager = new PhotoManagerImpl(datastore);
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

						
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
	
	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public List<User> getFollows() {
		this.follows = userManager.findFollows(user.getNick(), page);
		
		try {
				String bucketPropertyKey = "thumbnail.square.75px.bucket.name";
				String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
				photoURLs = new ArrayList<String>();
				
				for (User u:follows){
					Photo photo = photoManager.searchPhotoUniqueUtil(u.getAvatar());					
					photoURLs.add("/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+photo.getDataId());
				}
					
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return this.follows;
	}
	
	public void setFollows(List<User> follows) {
		this.follows = follows;
	}
	
	public List<User> getFollowers() {
		this.followers = userManager.findFollowers(user.getNick(), page);		
						
		try {
			String bucketPropertyKey = "thumbnail.square.75px.bucket.name";
			String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
			photoURLs = new ArrayList<String>();
			
			for (User u:followers){
				Photo photo = photoManager.searchPhotoUniqueUtil(u.getAvatar());					
				photoURLs.add("/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+photo.getDataId());
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
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
			
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public List<String> getPhotoURLs() {
		return photoURLs;
	}

	public void setPhotoURLs(List<String> photoURLs) {
		this.photoURLs = photoURLs;
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
	
	public String getThumbPhoto(String index){		
		int pos = Integer.valueOf(index);
		return this.photoURLs.get(pos);		
	}
	
}
