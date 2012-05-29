package org.mdissjava.mdisscore.view.album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@ViewScoped
@ManagedBean
public class AlbumBean {
	
	private String userNick;
	private String owner;
	private boolean showMenu;
	private String albumID;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	
	private List<List<String>> albumPhotosURLs;
	private List<String> photoURLs;
	
	private List<String> albumTitles;
	private List<String> albumIDs;
	
	private List<Album> albumList;
	
	private String informationMessage = "";
	
	public AlbumBean()
	{	
		System.out.println(this.informationMessage);
		
		//Depending on the logged user that is checking the albums, a different title is displayed		
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		
		if(loggedUser.equals(this.userNick))
		{
			this.owner = "My";
		}
		else
		{
			this.owner = this.userNick + "'s"; 
		}
		
		//get morphia database from properties and load the albums by its ids
		try {
			String database;
			this.albumTitles = new ArrayList<String>();
			this.albumIDs = new ArrayList<String>();
			this.albumPhotosURLs = new ArrayList<List<String>>();
			
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			AlbumManagerImpl albumManager = new AlbumManagerImpl(datastore);
			
			this.albumList = albumManager.findUserAlbums(this.userNick);
			
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			String bucketPropertyKey = "thumbnail.square.260px.bucket.name";
			String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
			
			for (Album al : albumList)
			{
				albumIDs.add(al.getAlbumId());
				albumTitles.add(al.getTitle());
				
				List<Photo> albumPhotos = al.getPhotos();	
				
				this.photoURLs = new ArrayList<String>();
				
				for (Photo p : albumPhotos)
				{
					String detailedPhotoURL = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+p.getDataId();
					this.photoURLs.add(detailedPhotoURL);	
				}
				
				this.albumPhotosURLs.add(photoURLs);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String deleteAlbum() {
		
	   FacesContext fc = FacesContext.getCurrentInstance();
	   Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		 
	   String user = params.get("user");
	   String albumID = params.get("albumID");
	   
	   System.out.println(user);
	   System.out.println(albumID);
			
	   try {
			String database;
			
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			AlbumManagerImpl albumManager = new AlbumManagerImpl(datastore);
			
			albumManager.deleteAlbum(this.albumID, user);
			
			this.informationMessage  = "<div class=\"alert alert-success\">" +
					"<button class=\"close\" data-dismiss=\"alert\">×</button>" +
					" A</div>";
			
	   } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }   
	   //This is used to force a page refresh
	   return "pretty:";
	}
	
	public void setDeletedAlbumId(AjaxBehaviorEvent event) {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
			 
		String albumID = params.get("albumID");
		
		this.albumID = albumID;
		
		System.out.println(albumID);
	}
		
	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}

	public boolean isShowMenu() {
		return showMenu;
	}

	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}	

	public List<String> getAlbumIDs() {
		return albumIDs;
	}

	public void setAlbumIDs(List<String> albumIDs) {
		this.albumIDs = albumIDs;
	}

	public List<Album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
	}

	public List<String> getPhotoURLs() {
		return photoURLs;
	}

	public void setPhotoURLs(List<String> photoURLs) {
		this.photoURLs = photoURLs;
	}

	public List<List<String>> getAlbumPhotosURLs() {
		return albumPhotosURLs;
	}

	public void setAlbumPhotosURLs(List<List<String>> albumPhotosURLs) {
		this.albumPhotosURLs = albumPhotosURLs;
	}

	public List<String> getAlbumTitles() {
		return albumTitles;
	}

	public void setAlbumTitles(List<String> albumTitles) {
		this.albumTitles = albumTitles;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getInformationMessage() {
		return informationMessage;
	}

	public void setInformationMessage(String informationMessage) {
		this.informationMessage = informationMessage;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
