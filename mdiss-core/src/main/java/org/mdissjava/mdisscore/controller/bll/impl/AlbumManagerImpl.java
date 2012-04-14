package org.mdissjava.mdisscore.controller.bll.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mdissjava.mdisscore.controller.bll.AlbumManager;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

/**
 * The album manager. This methods will be called from the beans
 * 
 * @author slok
 *
 */
public class AlbumManagerImpl implements AlbumManager{
	
	//TODO: Load from properties
	private final String DATABASE = "mdissphoto";
	//TODO: Load default album from properties
	private final String DEFAULT_ALBUM_TITLE = "Master";
	private AlbumDao albumDao;
	private Datastore datastore;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public AlbumManagerImpl(Datastore datastore) {
		this.datastore = datastore;
		this.albumDao = new AlbumDaoImpl(this.datastore);
	}

	public AlbumManagerImpl() {
		this.datastore = MorphiaDatastoreFactory.getDatastore(DATABASE);
		this.albumDao = new AlbumDaoImpl(this.datastore);
	}
	
	/**
	 * Creates a new album
	 * 
	 * @param albumTitle
	 * @param userNickname
	 * @throws IllegalArgumentException
	 */
	@Override
	public void insertAlbum(String albumTitle, String userNickname) throws IllegalArgumentException, IOException{
		
		if (albumTitle == null || userNickname == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		
		Album a = new Album();
		a.setTitle(albumTitle);
		a.setUserNick(userNickname);
		
		this.insertAlbum(a);
	}
	
	/**
	 * Creates a new album from the album pojo
	 * 
	 * @param album
	 * @throws IllegalArgumentException
	 * @throws IOException 
	 */
	@Override
	public void insertAlbum(Album album) throws IllegalArgumentException, IOException{
		
		if (album == null)
		{
			this.logger.error("Album argument is null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		
		this.logger.debug("Inserting new album {} from {}", album.getTitle(), album.getUserNick());
		
		if (this.findAlbum(album).size() > 0)
		{
			this.logger.error("{} album already exists in database", album.getTitle());
			throw new IOException(album.getTitle() + " album already exists in database");
		}
		this.albumDao.insertAlbum(album);
	}
	
	/**
	 * Adds a list of photos to a concrete album
	 * 
	 * @param userNickname
	 * @param albumTitle
	 * @param photos
	 * @throws IllegalArgumentException
	 */
	@Override
	public void addPhotoListToAlbum(String userNickname, String albumTitle, List<Photo> photos) throws IllegalArgumentException{
		if (userNickname == null || albumTitle == null || photos.isEmpty() || photos == null)
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		//TODO
	}
	
	/**
	 * Adds a photo to an album. The photo is new and it will be inserted in database too
	 * 
	 * @param userNickname
	 * @param albumTitle
	 * @param photo
	 * @throws IllegalArgumentException is thrown if the required arguments are wrong
	 * @throws IOException is thrown if the photo already exists in database 
	 */
	@Override
	public void addNewPhotoToAlbum(String userNickname, String albumTitle, Photo photo) throws IllegalArgumentException, IOException{
		
		
		if (userNickname == null || albumTitle == null || photo == null)
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		
		this.logger.debug("Adding new photo {} to {}", photo.getTitle(), albumTitle);

		//the album exists? (the search utility throws IOException)
		Album album = null;
		album = this.searchAlbumUniqueUtil(albumTitle, userNickname);
		
		PhotoManager photoManager = new PhotoManagerImpl(datastore);
		
		//check if the photo exists (this is not a new photo)
		if ( !photoManager.findPhoto(photo).isEmpty() )
		{
			this.logger.error("photo exists already in database. Use move instead");
			throw new IOException("photo exists already in database. Use move instead");
		}
		
		//We are setting the album although is set. Maybe is null or is bad set
		photo.setAlbum(album);
		
		//insert photo in database (before inserting a reference in a model , 
		//the reference needs to be inserted in its model)
		photoManager.insertPhoto(albumTitle, userNickname, photo);
		
		//add photo to album
		album.getPhotos().add(photo);
		
		//update the album
		this.updateAlbum(album);
		
	}
	
	/**
	 * Moves a photo to other album. This updates the photo reference to the new album 
	 * (deletes the old reference), deletes the photo reference from the album and
	 * adds the new reference to the photo in the new album
	 * 
	 */
	@Override
	public void movePhotoToAlbum(String userNickname, String albumTitle, String photoId) throws IllegalArgumentException, IOException{
		if (userNickname == null || albumTitle == null || photoId == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		
		//check if the photo exists already (if not, IOException)
		PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
		Photo photo = photoManager.searchPhotoUniqueUtil(photoId);
		
		this.movePhotoToAlbum(userNickname, albumTitle, photo);
		
	}
	
	/**
	 * Moves a photo to other album. This updates the photo reference to the new album 
	 * (deletes the old reference), deletes the photo reference from the album and
	 * adds the new reference to the photo in the new album
	 * 
	 */
	@Override
	public void movePhotoToAlbum(String userNickname, String albumTitle, Photo photo) throws IllegalArgumentException, IOException{
		if (userNickname == null || albumTitle == null || photo == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		this.logger.debug("moving photo {} to {}", photo.getTitle(), albumTitle);
		
		//the album exists? (the search util throws IOException)
		Album album = null;
		album = this.searchAlbumUniqueUtil(albumTitle, userNickname);
		
		//start the dance of albums :D
		
		//if the photo has a previous album then delete it
		if (photo.getAlbum() != null)
		{
			//delete photo from old album
			Album oldAlbum = photo.getAlbum();
			oldAlbum.getPhotos().remove(photo);
			this.updateAlbum(oldAlbum);
			
		}
		//insert photo in the new album
		album.getPhotos().add(photo);
		this.updateAlbum(album);
	
		//delete the reference to the old album in the photo and set the new one
		photo.setAlbum(album);

		PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
		photoManager.updatePhoto(photo);
	}
	
	/**
	 * updates an album
	 * 
	 */
	@Override
	public void updateAlbum(Album album) throws IllegalArgumentException
	{
		if (album == null)
		{
			this.logger.error("Album argument is null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		this.logger.debug("updating album");
		this.albumDao.updateAlbum(album);
	}
	
	/**
	 * Deletes an album from the pojo and moves the photos to the default album
	 * 
	 * @param album
	 * @throws IllegalArgumentException
	 * @throws IOException If the target is default album or the album doesn't exist
	 */
	public void deleteAlbum(Album album) throws IllegalArgumentException, IOException{
		
		if (album == null)
		{
			this.logger.error("Album argument is null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		if (album.getTitle().equals(DEFAULT_ALBUM_TITLE))
		{
			this.logger.error("Cannot delete the default album");
			throw new IllegalArgumentException("Cannot delete the default album");
		}
		
		this.logger.debug("Deleting album {}", album.getTitle());
		//final Album DEFAULT_ALBUM = this.searchAlbumUniqueUtil(DEFAULT_ALBUM_TITLE, album.getUserNick());
		
		//unload the reference to this album to all its photos
		//PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
		List<Photo> photoList = album.getPhotos();
		for(Photo i: photoList)
		{
			this.movePhotoToAlbum(album.getUserNick(), DEFAULT_ALBUM_TITLE, i);
		}
		
		this.albumDao.deleteAlbum(album);
		
	}

	/**
	 * Deletes an album from the id and the user 
	 * 
	 * @param albumId
	 * @param userNickname
	 * @throws IOException
	 */
	public void deleteAlbum(String albumId, String userNickname) throws IllegalArgumentException, IOException {
		
		if (albumId == null || userNickname == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		//if the album dowsn't exist then exception will be thrown
		Album album = this.searchAlbumUniqueUtil(albumId, userNickname);
		this.deleteAlbum(album);
		
	}
	
	/**
	 * Deletes all the data from the user, (all the albums with their photos)
	 * This includes the default album 
	 * 
	 */
	@Override
	public void deleteUserAllAlbumsAndPhotos(String userNickname) throws IllegalArgumentException, IOException {
		
		if (userNickname == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		this.logger.debug("Forcing the deletion of all the albums and photos of user {}", userNickname);
		PhotoManager photoManager = new PhotoManagerImpl(datastore);
		
		Album album = new Album();
		album.setUserNick(userNickname);
		
		List<Album> albumList = this.findAlbum(album);
		List<Photo> photoList = null;
		
		for (Album i: albumList)
		{
			 photoList = i.getPhotos();
			 
			 for(Photo j: photoList)
			 {
				 photoManager.deletePhoto(j);
				 //TODO: Delete all the image data an their thumbnails 
			 }
			
			//use dao because we want to delete default too
			this.albumDao.deleteAlbum(i);
		}
				
		
		
	}
	
	/**
	 * Find a list (many or not) of albums
	 * 
	 */
	@Override
	public List<Album> findAlbum(Album album) throws IllegalArgumentException{
		
		if (album == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		this.logger.debug("finding album list");
		return this.albumDao.findAlbum(album);
	}
	
	/**
	 * Searches the concrete album. This is needed because if we create a new album with the appropiate data
	 * that identifies the album this will not work because of the nature that Mongo has to give every object
	 * a ID (transparent for us)
	 * 
	 * @param albumTitle
	 * @param userNickname
	 * @return
	 * @throws IOException
	 */
	//package scope (other managers could use to search)
	Album searchAlbumUniqueUtil(String albumTitle, String userNickname) throws IOException
	{
		this.logger.debug("Searching for the album {} from {} user", albumTitle, userNickname);
		List<Album> aList = new ArrayList<Album>();
		Album a = new Album();
		
		a.setTitle(albumTitle);
		a.setUserNick(userNickname);
		
		aList = this.albumDao.findAlbum(a);
		
		if (aList.isEmpty())
		{
			this.logger.error("No {} album from {} named is stored in database", albumTitle, userNickname);
			throw new IOException("No " + albumTitle + " album from "+ userNickname +" named is stored in database");
		}
		
		int quantity = aList.size();
		if (quantity > 1)
		{
			this.logger.error("Too many albums found({}), expected only one", quantity);
			throw new IllegalStateException("Too many albums found(" + quantity + "), expected only one");
		}
		return aList.get(0);
		
	}

	
	
}