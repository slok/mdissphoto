package org.mdissjava.mdisscore.view.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class AdminManagerBean {
	

	private List<User> users;
		
	private UserManager userManager;	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private int MAX_NUMBER_CONNECTIONS = 5;
	
	private String thumbnailBucket = "square.75";
	private String thumbnailDatabase = "images";
	private Datastore datastore;
	
	private int totalUsers;
	private int page;
	
	PropertiesFacade propertiesFacade;
	String database;
	
	public AdminManagerBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.page = pb.getPage();
		if (this.page == 0){
			this.page = 1;
		}
		
		this.userManager = new UserManagerImpl();			
		totalUsers = userManager.getTotalUsers();
		users = new ArrayList<User>();
		users = userManager.findAllUsers(page, MAX_NUMBER_CONNECTIONS);	
		
		try {
			propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);	
			datastore = MorphiaDatastoreFactory.getDatastore(database);
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Error while retrieveing connections from the database", "Connections"));
		}					
	}
	
	public void deleteUser(User user){
		this.userManager.deleteUser(user);
	}
				
	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMaxNumberConnections(){
		return this.MAX_NUMBER_CONNECTIONS;
	}
	
	public void setMaxNumberConnections(int max){
		this.MAX_NUMBER_CONNECTIONS = max;
	}

	public String getThumbnailBucket() {
		return this.thumbnailBucket;
	}

	public void setThumbnailBucket(String thumbnailBucket) {
		this.thumbnailBucket = thumbnailBucket;
	}

	public String getThumbnailDatabase() {
		return this.thumbnailDatabase;
	}

	public void setThumbnailDatabase(String thumbnailDatabase) {
		this.thumbnailDatabase = thumbnailDatabase;
	}
		
	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	

	
	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}	
	
}
