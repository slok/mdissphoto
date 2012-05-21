package org.mdissjava.mdisscore.view.album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class AlbumDetailsBean {
	
	private String userNick;
	private String albumId;
	private String albumTitle;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	
	private List<String> photoURLs;
	private List<String> photoTitles;
	private List<String> photoIDs;
	private List<Photo> photoList;
	
	public AlbumDetailsBean()
	{	
		//TODO: Replace this with DB logic!
		/*
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.albumTitle = "AlbumTitle";
		
		this.photoURLs = new ArrayList<String>();
		
		this.photoURLs.add("http://www.trucospc.info/fondos-de-pantalla/Naturaleza/imagenes/Snow%20Mountain.jpg");
		this.photoURLs.add("http://www.gjxu.com/uploads/Mountain-Wallpaper1.jpg");
		this.photoURLs.add("http://2.bp.blogspot.com/_Hrh98i7uFqo/TSX0BAlLewI/AAAAAAAAACg/6jIG1FSxdKU/s1600/Great+Snow+Mountain.jpg");
		this.photoURLs.add("http://www.dummyimage.com/260x260/");
		
		this.photoTitles = new ArrayList<String>();
		this.photoTitles.add("Photo 1");
		this.photoTitles.add("Photo 2");
		this.photoTitles.add("Photo 3");
		this.photoTitles.add("Photo 4");
		*/
		
		//get morphia database from properties and load the albums by its ids
		try {
			String database;
			this.photoURLs = new ArrayList<String>();
			this.photoTitles = new ArrayList<String>();
			this.photoIDs = new ArrayList<String>();
			this.photoList = new ArrayList<Photo>();
			
			ParamsBean pb = getPrettyfacesParams();
			this.userNick = pb.getUserId();
			this.albumId = pb.getAlbumId();
			
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			AlbumManagerImpl albumManager = new AlbumManagerImpl(datastore);
			
			Album a = albumManager.searchAlbumUniqueUtil(this.albumId, this.userNick);
			
			this.albumTitle = a.getTitle();	
			this.photoList = albumManager.getPhotosFromAlbum(this.albumId, this.userNick);
			
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

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
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

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
