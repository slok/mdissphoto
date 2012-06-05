package org.mdissjava.mdisscore.view.explore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.commonutils.utils.Utils;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class ExploreBean {
	
	private List<String> photoURLs;
	private List<String> photoTitles;
	private List<String> photoUsers;
	
	private List<Photo> photoList;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final int PHOTO_QUANTITY = 14;
	
	public ExploreBean()
	{	
		try {
			String database;
			this.photoURLs = new ArrayList<String>();
			this.photoTitles = new ArrayList<String>();
			this.photoUsers = new ArrayList<String>();
			
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
			
			this.photoList = photoManager.getRandomPhotos(PHOTO_QUANTITY);
			
			String imageDatabase = propertiesFacade.getProperties("globals").getProperty("images.db");
			
			Utils utils = new Utils();
			
			//Start distributing photo info into different arrays
			for (Photo p : this.photoList)
			{
				this.photoTitles.add(p.getTitle());
				this.photoUsers.add(p.getAlbum().getUserNick());
				
				//Get random size for the photo
				List<String> sizeList = Arrays.asList("240", "320");
				int randNum = utils.getRandomNumInRange(0, 1);
				
				String bucketPropertyKey = "thumbnail.scale." + sizeList.get(randNum) + "px.bucket.name";
				String bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
							
				String detailedPhotoURL = "/dynamic/image?db="+imageDatabase+"&amp;bucket="+bucket+"&amp;id="+p.getDataId();
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

	public List<String> getPhotoUsers() {
		return photoUsers;
	}

	public void setPhotoUsers(List<String> photoUsers) {
		this.photoUsers = photoUsers;
	}

	public int getPHOTO_QUANTITY() {
		return PHOTO_QUANTITY;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
