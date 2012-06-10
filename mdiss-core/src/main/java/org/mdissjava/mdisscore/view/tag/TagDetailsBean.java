package org.mdissjava.mdisscore.view.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.TagManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@ViewScoped
@ManagedBean
public class TagDetailsBean {
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private TagManagerImpl tagManager;
	
	private String userNick;
	private String tag;	
	private List<Photo> photos;
	private Map<String,String> detailedPhotoURL;	
	
	public TagDetailsBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.userNick =  pb.getUserId();
		this.tag = pb.getTag();
		
		
		try {
			//get morphia database from properties and load the photo by its id
			String database;
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			tagManager = new TagManagerImpl(datastore);
			
			//get the photo list which contains that tag
			this.photos = tagManager.getPhotosFromTag(this.tag);
			
			this.detailedPhotoURL = new HashMap<String, String>();
			//get the PhotoURL from all the photos 
			for (Photo p : this.photos) {												
				
				//get the database of the photos and create the url with the appropiate image
				String bucketPropertyKey = "images.bucket";
				String bucket = propertiesFacade.getProperties("globals").getProperty(bucketPropertyKey);
				bucket = "square.75";
				
				database = propertiesFacade.getProperties("globals").getProperty("images.db");
				// get the photoURL which contains that tag				
				this.detailedPhotoURL.put(p.getPhotoId(), "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+p.getPhotoId());				
			}
						
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
				
	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
		
	public List<Photo> getPhotosFromTag() {
		return photos;
	}

	public void setPhotosFromTag(List<Photo> photosFromTag) {
		this.photos = photosFromTag;
	}

	public Map<String, String> getDetailedPhotoURL() {
		return detailedPhotoURL;
	}

	public void setDetailedPhotoURL(Map<String, String> detailedPhotoURL) {
		this.detailedPhotoURL = detailedPhotoURL;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
		
	
}