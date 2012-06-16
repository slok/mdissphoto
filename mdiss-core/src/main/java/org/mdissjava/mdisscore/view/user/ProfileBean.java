package org.mdissjava.mdisscore.view.user;

import java.io.IOException;

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

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class ProfileBean {

	private UserManager userManager;	
	private PhotoManager photoManager;
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	
	private String userNick;
	private User user;
	private String profileAvatar;
	
	public ProfileBean(){
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();	
		this.userManager = new UserManagerImpl();					
		this.user = userManager.getUserByNick(this.userNick);	
		
		String database;
		PropertiesFacade propertiesFacade;
		try {
			propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			photoManager = new PhotoManagerImpl(datastore);
			
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			String bucketPropertyKey = "thumbnail.square.150px.bucket.name";
			String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
			
			Photo photo = photoManager.searchPhotoUniqueUtil(this.user.getAvatar());	
			this.profileAvatar = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+photo.getDataId();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProfileAvatar() {
		return profileAvatar;
	}

	public void setProfileAvatar(String profileAvatar) {
		this.profileAvatar = profileAvatar;
	}

	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
}
