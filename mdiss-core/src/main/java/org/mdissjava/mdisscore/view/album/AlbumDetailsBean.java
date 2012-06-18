package org.mdissjava.mdisscore.view.album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@ViewScoped
@ManagedBean
public class AlbumDetailsBean {
	
	private String userNick;
	private String albumID;
	private String albumTitle;
	private boolean showMenu;
	private boolean showMessage;
	private String message;
	private String errorMessage;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	
	private int MAX_NUMBER_PHOTOS = 4;
	
	private List<String> photoURLs;
	private List<String> photoTitles;
	private List<String> photoIDs;
	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	private List<Photo> photoList;

	private int totalPhotosAlbum;
	private PhotoManager photoManager;	
	private int page;
	
	public AlbumDetailsBean()
	{			
		//Depending on the logged user that is checking the albums, modifyAlbum button is shown or not.	
		ParamsBean pb = getPrettyfacesParams();
		this.page = pb.getPage();
		if (this.page == 0){
			this.page = 1;
		}
		this.userNick = pb.getUserId();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		
		if(loggedUser.equals(this.userNick))
		{
			this.showMenu = true;
		}
		else
		{
			this.showMenu = false;
		}
		
		this.message = "";
		this.showMessage = false;
		System.out.println("ENTRa");
		
		//get morphia database from properties and load the albums by its ids
		try {
			String database;
			this.photoURLs = new ArrayList<String>();
			this.photoTitles = new ArrayList<String>();
			this.photoIDs = new ArrayList<String>();
			this.photoList = new ArrayList<Photo>();
			
			this.albumID = pb.getAlbumId();
			
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			AlbumManagerImpl albumManager = new AlbumManagerImpl(datastore);
			photoManager = new PhotoManagerImpl(datastore);
			
			Album a = albumManager.searchAlbumUniqueUtil(this.albumID, this.userNick);
			
			
			this.albumTitle = a.getTitle();	
//			this.photoList = albumManager.getPhotosFromAlbum(this.albumID, this.userNick);	
			totalPhotosAlbum = photoManager.getTotalPhotosAlbum(a);
			this.photoList = photoManager.getPhotosAlbumOffset(a, MAX_NUMBER_PHOTOS, (page-1) * MAX_NUMBER_PHOTOS);
			
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			String bucketPropertyKey = "thumbnail.square.260px.bucket.name";
			String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
				
			this.photoURLs = new ArrayList<String>();
			for (Photo p : this.photoList)
			{
				this.photoIDs.add(p.getPhotoId());
				this.photoTitles.add(p.getTitle());
				String detailedPhotoURL = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+p.getDataId();
				this.photoURLs.add(detailedPhotoURL);	
			}
				
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void modifyTitle(AjaxBehaviorEvent event) {
		
		this.showMessage = false;
		
		//Security check just to ensure that the one erasing the album is the owner of the album 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		
		if(loggedUser.equals(this.userNick)) {
			 
			 try {
					String database;
					
					PropertiesFacade propertiesFacade = new PropertiesFacade();
					database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
				
					Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
					AlbumManagerImpl albumManager = new AlbumManagerImpl(datastore);
					
					//Find in the DB the album to be modified
					Album album = albumManager.searchAlbumUniqueUtil(this.albumID, this.userNick);
					//Change the album title to the new one
					album.setTitle(this.albumTitle);					
					albumManager.updateAlbum(album);
					
					this.message = "Album name succesfully changed!";
					this.showMessage = true;
					
			 } catch (IllegalArgumentException e) {
				this.errorMessage = "ERROR! Couldn't change album name.";
				e.printStackTrace();
			 } catch (IOException e) {
				this.errorMessage = "ERROR! Couldn't change album name.";
				e.printStackTrace();
			 }   
		}
		
	}
	
	
	public int getTotalPhotosAlbum() {		
		return this.totalPhotosAlbum;
	}

	public void setTotalPhotosAlbum(int totalPhotosAlbum) {
		this.totalPhotosAlbum = totalPhotosAlbum;
	}

	public int getMaxNumberPhotos() {
		return MAX_NUMBER_PHOTOS;
	}

	public void setMaxNumberPhotos(int maxNumberPhotos) {
		this.MAX_NUMBER_PHOTOS = maxNumberPhotos;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<String> getPhotoIDs() {
		return photoIDs;
	}

	public void setPhotoIDs(List<String> photoIDs) {
		this.photoIDs = photoIDs;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumId(String albumID) {
		this.albumID = albumID;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public List<String> getPhotoURLs() {
		return photoURLs;
	}

	public void setPhotoURLs(List<String> photoURLs) {
		this.photoURLs = photoURLs;
	}

	public List<String> getPhotoTitles() {
		return photoTitles;
	}

	public void setPhotoTitles(List<String> photoTitles) {
		this.photoTitles = photoTitles;
	}

	public boolean isShowMenu() {
		return showMenu;
	}

	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
