package org.mdissjava.mdisscore.view.photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class PhotoDetailsBean {
	
	private String photoId;
	private String userNick;
	
	private List<String> defaultPhotoSizes;
	private List<String> thumbnailIds;
	private String thumbnailBucket;
	private String thumbnailDatabase;
	
	private String detailedPhotoURL;
	
	private Photo photo;
	
	
	public PhotoDetailsBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.photoId = pb.getPhotoId();
		
		//TODO: check if isn't detailed to redirect to /user/xxx/upload/details/yyy-yyyyyy-yyyy-yyy
		
		
		try {
			//get morphia database from properties and load the photo by its id
			String database;
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties("globals").getProperty("morphia.db");
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
			
			this.photo = photoManager.searchPhotoUniqueUtil(photoId);
			
			
			//search the available sizes for this photo
			int sizes[] = {100, 240, 320, 500, 640, 800, 1024}; //our different sizes
			this.defaultPhotoSizes = new ArrayList<String>();
			int photoSize = 640;
			
			for (int i: sizes)
			{
				//if i is bigger than our photo size then don't add to the list of available sizes for this photo
				if (photoSize >= i)
				{
					this.defaultPhotoSizes.add(String.valueOf(i));
				}
			}
			
			
			// we want to know wich is the best photo for the display of the detail. 
			// Max is 640px but some photos are smaller than 640px so we set the original size
			//and if the photo is bigger than 640 then set the size to 640
			//get the database of the photos and create the url with the appropiate image
			
			String bucket;
			String bucketPropertyKey = null;
			if(photoSize >= 640)//640px size
			{
				bucketPropertyKey = "thumbnail.scale.640px.bucket.name";
				bucket = propertiesFacade.getProperties("thumbnails").getProperty(bucketPropertyKey);
			}
			else//original size
			{
				bucketPropertyKey = "images.bucket";
				bucket = propertiesFacade.getProperties("globals").getProperty(bucketPropertyKey);
			}
			
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			this.detailedPhotoURL = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+this.photoId;
			
			
			//set the album thumbnails identifiers and necessary data
			this.thumbnailIds= new ArrayList<String>();
			Album album = this.photo.getAlbum();
			this.thumbnailBucket = "square.75";
			this.thumbnailDatabase = database;
			
			for (Photo i: album.getPhotos())
			{
				if(!i.getPhotoId().equals(this.photoId))
					this.thumbnailIds.add(i.getPhotoId());
			}
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	public List<String> getDefaultPhotoSizes() {
		return defaultPhotoSizes;
	}

	public void setDefaultPhotoSizes(List<String> defaultPhotoSizes) {
		this.defaultPhotoSizes = defaultPhotoSizes;
	}
	
	public String getThumbnailDatabase() {
		return thumbnailDatabase;
	}

	public void setThumbnailDatabase(String thumbnailDatabase) {
		this.thumbnailDatabase = thumbnailDatabase;
	}

	public List<String> getThumbnailIds() {
		return thumbnailIds;
	}

	public void setThumbnailIds(List<String> thumbnailIds) {
		this.thumbnailIds = thumbnailIds;
	}

	public String getThumbnailBucket() {
		return thumbnailBucket;
	}

	public void setThumbnailBucket(String thumbnailBucket) {
		this.thumbnailBucket = thumbnailBucket;
	}

	public String getDetailedPhotoURL() {
		return detailedPhotoURL;
	}

	public void setDetailedPhotoURL(String detailedPhotoURL) {
		this.detailedPhotoURL = detailedPhotoURL;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
	
	

}
