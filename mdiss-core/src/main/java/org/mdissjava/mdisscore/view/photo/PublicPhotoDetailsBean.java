package org.mdissjava.mdisscore.view.photo;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class PublicPhotoDetailsBean {
	
	private String photoTitle;
	private String ownerUsername;
	private String photoUrl;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final int MAX_PHOTO_SIZE = 800;
	
	public PublicPhotoDetailsBean() {
		
		try {
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			
			String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
			String bucket = "";
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			
			//get the photo
			PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
			Photo photo;
			photo = photoManager.searchPhotoUniqueUtil(this.getPrettyfacesParams().getPhotoId());
			
			
			//get the size of the photo
			int height = photo.getMetadata().getResolutionREAL().getHeight();
			int width = photo.getMetadata().getResolutionREAL().getWidth();
			int photoSize = height > width ? height: width;
			
			
			if(photoSize >= this.MAX_PHOTO_SIZE)
				bucket = "scale.800";
			else{
				
					bucket = "original";
			}
			
			this.photoTitle = photo.getTitle();
			this.ownerUsername = photo.getAlbum().getUserNick();
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			this.photoUrl = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+photo.getPhotoId();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPhotoTitle() {
		return photoTitle;
	}
	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}
	public String getOwnerUsername() {
		return ownerUsername;
	}
	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
