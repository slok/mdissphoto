package org.mdissjava.mdisscore.view.album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class AlbumBean {
	
	private String userNick;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final String RESOLUTIONS_PROPS_KEY = "resolutions";
	
	private List<List<String>> albumPhotosURLs;
	private List<String> photoURLs;
	
	private List<String> albumTitles;
	private List<String> albumIDs;
	
	private List<Album> albumList;
	
	public AlbumBean()
	{	
		//TODO: Replace this with DB logic!
		/*
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.photoId = pb.getPhotoId();
		
		this.photoURLs = new ArrayList<String>();
		
		this.photoURLs.add("http://www.trucospc.info/fondos-de-pantalla/Naturaleza/imagenes/Snow%20Mountain.jpg");
		this.photoURLs.add("http://www.gjxu.com/uploads/Mountain-Wallpaper1.jpg");
		this.photoURLs.add("http://2.bp.blogspot.com/_Hrh98i7uFqo/TSX0BAlLewI/AAAAAAAAACg/6jIG1FSxdKU/s1600/Great+Snow+Mountain.jpg");
		this.photoURLs.add("http://www.dummyimage.com/200x200/");
		
		this.albumTitles = new ArrayList<String>();
		this.albumPhotosURLs = new ArrayList<List<String>>();
		
		this.albumTitles.add("Album 1");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 2");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 3");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 4");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 5");
		this.albumPhotosURLs.add(photoURLs); 
		*/
		
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		
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
				System.out.println("---------------Album-----------------");
				albumIDs.add(al.getAlbumId());
				System.out.println(albumIDs);
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

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
