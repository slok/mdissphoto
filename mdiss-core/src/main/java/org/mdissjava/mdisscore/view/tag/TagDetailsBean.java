package org.mdissjava.mdisscore.view.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
	private final String RESOLUTIONS_PROPS_KEY = "resolutions";
	private TagManagerImpl tagManager;
	
	private String userNick;
	private String tag;	
	private List<Photo> photos;
	private Map<String,String> detailedPhotoURL;	
	private List<String> defaultPhotoSizes;
	private final int PHOTO_SHOW_SIZE = 640;
	
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
			
			//search the available sizes for this photo
			//int sizes[] = {100, 240, 320, 500, 640, 800, 1024}; //our different sizes
			Properties allResolutions = propertiesFacade.getProperties(RESOLUTIONS_PROPS_KEY);
			
			this.defaultPhotoSizes = new ArrayList<String>();
			
			this.detailedPhotoURL = new HashMap<String, String>();
			//get the PhotoURL from all the photos 
			for (Photo p : this.photos) {				
				//set the size
				int height = p.getMetadata().getResolutionREAL().getHeight();
				int width = p.getMetadata().getResolutionREAL().getWidth();
				System.out.println("height: " + height);
				System.out.println("height: " + height);
				
				int photoSize = height > width ? height: width;
				
				// we get all the available resolutions
				@SuppressWarnings("rawtypes")
				Enumeration resolutions = allResolutions.keys();
				
				String key;
				//for each one we check if is scalar one and not square and if is smaller than the photo
				while(resolutions.hasMoreElements())
				{
					key = (String)resolutions.nextElement();
					System.out.println("key: " + key);
					
					//Only needed the scale ones, not the squares
					if (allResolutions.getProperty(key).contains("scale"))
					{
						//if is bigger than our photo size then don't add to the list of available sizes for this photo
						if (photoSize >= Integer.valueOf(key))
						{
							this.defaultPhotoSizes.add(key);
						}
					}
					
				}
				
				
				
				// we want to know which is the best photo for the display of the detail. 
				// Max is 640px but some photos are smaller than 640px so we set the original size
				//and if the photo is bigger than 640 then set the size to 640
				//get the database of the photos and create the url with the appropiate image
				String bucket;
				String bucketPropertyKey = null;
				if(photoSize > PHOTO_SHOW_SIZE)//640px size
				{
					System.out.println("mayor");
					bucketPropertyKey = "thumbnail.scale." + PHOTO_SHOW_SIZE + "px.bucket.name";
					bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
					bucket = "square.75";
				}
				else//original size
				{
					System.out.println("menor");
					bucketPropertyKey = "images.bucket";
//					bucket = propertiesFacade.getProperties("globals").getProperty(bucketPropertyKey);
					bucket = "square.75";
				}
				
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

	public List<String> getDefaultPhotoSizes() {
		return defaultPhotoSizes;
	}
	
	public void setDefaultPhotoSizes(List<String> defaultPhotoSizes) {
		this.defaultPhotoSizes = defaultPhotoSizes;
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