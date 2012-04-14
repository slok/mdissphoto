package org.mdissjava.mdisscore.view.upload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ViewScoped
@ManagedBean
public class UploadDetailsBean {

	private String userID;
	private String imageID;
	private String title;
	private String titleResult;
	private String tags; //need to tokenize
	private HashMap<String, Boolean> publicPhotoList;
	private boolean publicPhotoScope;
	private boolean plus18;
	private String album;
	private String license;
	
	private HashMap<String, String> albums;
	private HashMap<String, String> licenses;
	private String imageURL;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	

	public UploadDetailsBean() {
		//load the needed data
		logger.info("Creating Upload details instance");
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);	
		this.imageID = pb.getPhotoId();
		
		//TODO: Get the user from session
		this.userID = "slok";
		
		//TODO: load metadata (Maite)
		
		//TODO:Load the best fitting image (320 -> 240 -> 150 -> 100 -> 75 -> 30)
		String db = "thumbnails";
		String bucket = "scale.320";
		//TODO: Load the image url
		this.imageURL = "/dynamic/image?db="+db+"&amp;bucket="+bucket+"&amp;id="+this.imageID;
		
		//set the radiobuttons for public and private
		this.publicPhotoList = new HashMap<String, Boolean>();
		this.publicPhotoList.put("Public", true);
		this.publicPhotoList.put("Private", false);
		
		//TODO: Load the albums
		this.albums = new HashMap<String, String>();
		this.albums.put("animals", "animals");
		this.albums.put("places", "places");
		this.albums.put("summer 2009", "summer 2009");
		
		//TODO: Load the licenses
		this.licenses = new HashMap<String, String>();
		this.licenses.put("CC 2.0", "CC 2.0");
		this.licenses.put("GPL", "GPL");
		this.licenses.put("AGPL", "AGPL");
		this.licenses.put("C", "C");
		
		//TODO: Is there some data already in the database (previous details)?
		//If yes, load it!
		
		//default public
		this.publicPhotoScope = true;
	}

	public void saveDetails()
	{
		
		
	}
	
	/**
	 * unchecks public checkbox if +18 is active and viceversa
	 * 
	 * @param event
	 */
	public void plus18Validator(AjaxBehaviorEvent event)
	{
		System.out.println(this.plus18 + ":" + this.publicPhotoScope);
		
		String component = event.getComponent().getId();
		System.out.println(component);
		
		//if plus18 component has triggered the event then plublic needs to be updated
		//otherwise public has triggered the event and plus18 needs to be updated
		if (component.equals("plus18"))
		{	
			if (this.plus18 && this.publicPhotoScope)
			{
				this.publicPhotoScope= false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Public is marked", "Check private scope if you want a +18 photo"));
			}
			
		}else
		{
			if (this.plus18 && this.publicPhotoScope)
			{
				this.plus18 = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"+18 is marked", "Uncheck +18 if you want public scope for the photo"));
			}
		}
	}

	public void titleValidator(AjaxBehaviorEvent event)
	{  
		System.out.println("Checking name: "+this.title);
		//TODO: Check if the image name exists already 
		if (this.title.contains("info"))
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"OK", null));  
		if (this.title.contains("error"))
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Duplicated name"));
		
	}
	

	public HashMap<String, Boolean> getPublicPhotoList() {
		return publicPhotoList;
	}

	public void setPublicPhotoList(HashMap<String, Boolean> publicPhotoList) {
		this.publicPhotoList = publicPhotoList;
	}

	public boolean isPublicPhotoScope() {
		return publicPhotoScope;
	}

	public void setPublicPhotoScope(boolean publicPhotoScope) {
		this.publicPhotoScope = publicPhotoScope;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitleResult() {
		return titleResult;
	}


	public void setTitleResult(String titleResult) {
		this.titleResult = titleResult;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public boolean isPlus18() {
		return plus18;
	}


	public void setPlus18(boolean plus18) {
		this.plus18 = plus18;
	}


	public String getAlbum() {
		return album;
	}


	public void setAlbum(String album) {
		this.album = album;
	}


	public String getLicense() {
		return license;
	}


	public void setLicense(String license) {
		this.license = license;
	}


	public HashMap<String, String> getAlbums() {
		return albums;
	}


	public void setAlbums(HashMap<String, String> albums) {
		this.albums = albums;
	}


	public HashMap<String, String> getLicenses() {
		return licenses;
	}


	public void setLicenses(HashMap<String, String> licenses) {
		this.licenses = licenses;
	}


	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	
}
