package org.mdissjava.mdisscore.view.photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.commonutils.utils.Utils;
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
public class PhotoDetailsSettingsBean {
	
	private String photoId;
	private String userNick;
		
	private Photo photo;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";

	private String selectedAlbum; 
	private List<String> albumTitles;
	private int totalVotesPoints;
	private String myTags;
	private PhotoManagerImpl photoManager;
	private AlbumManagerImpl albumManager;
	private List<Photo> photosFromTag;
	private List<Album> albumList;
	private String misTags;

	
	public PhotoDetailsSettingsBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.photoId = pb.getPhotoId();
		
		try {
			//get morphia database from properties and load the photo by its id
			String database;
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			photoManager = new PhotoManagerImpl(datastore);
			albumManager = new AlbumManagerImpl(datastore);
			
			this.photo = photoManager.searchPhotoUniqueUtil(photoId);
									
			//get the album from the photo
			String albumTitle = this.photo.getAlbum().getTitle();
			//get all the albums from the userNick
			this.albumList = albumManager.findUserAlbums(this.userNick);			
			this.albumTitles = new ArrayList<String>();
			List<String> otherAlbumTitles = new ArrayList<String>();
			for (Album a: albumList)
			{	
				//insert the first the album of that photo
				if(a.getTitle().equals(albumTitle)){
					this.albumTitles.add(a.getTitle());
				}
				else {
					otherAlbumTitles.add(a.getTitle());
				}
			}
			//add the others map at the end of the list
			this.albumTitles.addAll(otherAlbumTitles);
			
			// get total votes from the photoId
			this.totalVotesPoints = photoManager.getTotalVotesFromPhoto(this.photoId);
			
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {		
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	public List<String> getAlbumTitles() {
		return this.albumTitles;
	}
	
	public void setAlbumTitles(List<String> albumTitles) {
		this.albumTitles = albumTitles;
	}
	
	public String getSelectedAlbum() { 
		return selectedAlbum;
	}
	
	public void setSelectedAlbum(String selectedAlbum) {
		this.selectedAlbum = selectedAlbum;
	}

	public int getTotalVotesPoints() {
		return totalVotesPoints;
	}
	
	public void setTotalVotesPoints(int totalVotesPoints) {
		this.totalVotesPoints = totalVotesPoints;
	}

	public String getMyTags() {
		List<String> tags = this.photo.getTags();
		myTags = tags.toString();
		return myTags;
	}

	public void setMyTags(String myTags) {
		this.myTags = myTags;
	}
	
	public List<Photo> getPhotosFromTag() {
		return photosFromTag;
	}

	public void setPhotosFromTag(List<Photo> photosFromTag) {
		this.photosFromTag = photosFromTag;
	}

	public List<Album> getAlbumList() {
		return albumList;
	}
	
	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
	}
	
	public String getMisTags() {
		return misTags;
	}

	public void setMisTags(String misTags) {
		System.out.println("Mistags: " + misTags);
		this.misTags = misTags;
	}
		
	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
	
	public String deleteAlbum() {
		
		ParamsBean pb = getPrettyfacesParams();
		pb.setAlbumId(this.photo.getAlbum().getAlbumId());
		
		//Security check just to ensure that the one erasing the photo is the owner of the album 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		
		if(loggedUser.equals(this.userNick)) {
					
			 try {
					String database;
					
					PropertiesFacade propertiesFacade = new PropertiesFacade();
					database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
				
					Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
					PhotoManager photoManager = new PhotoManagerImpl(datastore);
					
					photoManager.deletePhoto(this.photoId);
					
			 } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }   
		}
		return "pretty:album-detail";
	}
		
	public void saveSettings()
	{
		System.out.println("saveSettings");
		String allTags = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("misTags");
		System.out.println("allTags: " + allTags);		
		System.out.println("title: " + photo.getTitle());
		System.out.println("PhotoDetailsBean.doSave()");
		Album readNewAlbum = null;
		
		//add the album to database
			if(!this.photo.getTitle().equals("")){
		
				//get the photo
				this.photo.setTitle(this.photo.getTitle());
									
				//get the tags
				System.out.println("Tags photo: " + this.photo.getTags());
				if (!allTags.equals(""))
				{ 
					//get the list of tags from the photo
					List<String> newTagList = Utils.splitTags(allTags, ",");
					// add the new tags to the prevous list stored in db
					System.out.println("tags: " + newTagList.toString());
					this.photo.setTags(newTagList);						
				}			
				//update modified photo
				this.photoManager.updatePhoto(this.photo);

				
				//get the new object album from title selected
				Album newAlbum = new Album();
				newAlbum.setTitle(this.selectedAlbum);
				List<Album> readListAlbum = albumManager.findAlbum(newAlbum);
				//add the new photo to the album has found previously 
				if(readListAlbum != null) {
					readNewAlbum = readListAlbum.get(0);
					// check if newAlbum is the same, if there are not the same move the album
					if(!readNewAlbum.getAlbumId().equals(this.photo.getAlbum().getAlbumId())) {
						try {
							albumManager.movePhotoToAlbum(this.userNick, readNewAlbum.getAlbumId(), this.photo);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}						
						
					}
					
				}
				
				//Navigation to photo-detail view
				String outcome = "pretty:photo-detail";
				FacesContext facesContext =  FacesContext.getCurrentInstance();
				facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
			}
			
	}
	
}
