package org.mdissjava.mdisscore.controller.bll.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mdissjava.mdisscore.controller.bll.AlbumManager;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
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
	 * @return the album id
	 * @throws IllegalArgumentException
	 */
	@Override
	public String insertAlbum(String albumTitle, String userNickname) throws IllegalArgumentException, IOException{
		
		if (albumTitle == null || userNickname == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		
		Album a = new Album();
		a.setTitle(albumTitle);
		a.setUserNick(userNickname);
		
		//the uuid is created in this insert
		return this.insertAlbum(a);
	}
	
	/**
	 * Creates a new album from the album pojo
	 * 
	 * @param album
	 * @throws IllegalArgumentException
	 * @throws IOException 
	 */
	@Override
	public String insertAlbum(Album album) throws IllegalArgumentException, IOException{
		
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
		
		//set the album id if necessary
		String albumId = album.getAlbumId();
		if (albumId == null)
			album.setAlbumId(UUID.randomUUID().toString());
		
		this.albumDao.insertAlbum(album);
		return album.getAlbumId();
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
	public void addPhotoListToAlbum(String userNickname, String albumId, List<Photo> photos) throws IllegalArgumentException{
		if (userNickname == null || albumId == null || photos.isEmpty() || photos == null)
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
	public void addNewPhotoToAlbum(String userNickname, String albumId, Photo photo) throws IllegalArgumentException, IOException{
		
		
		if (userNickname == null || albumId == null || photo == null)
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		
		this.logger.debug("Adding new photo {} to {}", photo.getTitle(), albumId);

		//the album exists? (the search utility throws IOException)
		Album album = null;
		album = this.searchAlbumUniqueUtil(albumId, userNickname);
		
		PhotoManager photoManager = new PhotoManagerImpl(datastore);
		
		//check if the photo exists (this is not a new photo)
		//check by photoId only
		Photo pTemp = new Photo();
		pTemp.setPhotoId(photo.getPhotoId());
		if ( !photoManager.findPhoto(pTemp).isEmpty() )
		{
			this.logger.error("photo exists already in database. Use move instead");
			throw new IOException("photo exists already in database. Use move instead");
		}
		
		//We are setting the album although is set. Maybe is null or is bad set
		photo.setAlbum(album);
		
		//insert photo in database (before inserting a reference in a model , 
		//the reference needs to be inserted in its model)
		try{
		new PhotoDaoImpl(this.datastore).insertPhoto(photo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
	public void movePhotoToAlbum(String userNickname, String albumId, String photoId) throws IllegalArgumentException, IOException{
		if (userNickname == null || albumId == null || photoId == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		
		//check if the photo exists already (if not, IOException)
		PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
		Photo photo = photoManager.searchPhotoUniqueUtil(photoId);
		
		this.movePhotoToAlbum(userNickname, albumId, photo);
		
	}
	
	/**
	 * Moves a photo to other album. This updates the photo reference to the new album 
	 * (deletes the old reference), deletes the photo reference from the album and
	 * adds the new reference to the photo in the new album
	 * 
	 */
	@Override
	public void movePhotoToAlbum(String userNickname, String albumId, Photo photo) throws IllegalArgumentException, IOException{
		if (userNickname == null || albumId == null || photo == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Some argument(s) is/are null, can't continue with the action");
		}
		
		this.logger.debug("moving photo {} to {}", photo.getTitle(), albumId);
		
		//the album exists? (the search util throws IOException)
		Album album = null;
		album = this.searchAlbumUniqueUtil(albumId, userNickname);
		
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
	 * Deletes default album. WARNING! DO NOT USE! Only for jUnitTests!
	 * 
	 * @param album
	 * @throws IOException If the target is default album or the album doesn't exist
	 */
	@Override
	public void forceDeleteAlbum(Album album) throws IOException{
		
		if (album == null)
		{
			this.logger.error("Album argument is null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		if (album.getTitle().equals(DEFAULT_ALBUM_TITLE))
		{
			this.logger.debug("Deleting album {}", album.getTitle());
			//final Album DEFAULT_ALBUM = this.searchAlbumUniqueUtil(DEFAULT_ALBUM_TITLE, album.getUserNick());
			
			//unload the reference to this album to all its photos
			//PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
			//TODO: get the defailt album Id
			try{
				//get the default album Id
				Album masterAlbum = new Album();
				masterAlbum.setTitle(DEFAULT_ALBUM_TITLE);
				masterAlbum.setUserNick(album.getUserNick());
				String masterAlbumId = this.findAlbum(masterAlbum).get(0).getAlbumId();

				
				List<Photo> photoList = album.getPhotos();
				//if there aren't any photos, then we don't need to move
				if (photoList != null)
				{
					for(Photo i: photoList)
					{
						this.movePhotoToAlbum(album.getUserNick(), masterAlbumId, i);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			this.albumDao.deleteAlbum(album);
		}
		else
		{
			this.logger.error("This is NOT default album. Use method delete for deleting this album");
			throw new IllegalArgumentException("This is NOT default album. Use method delete for deleting this album");
		}
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
		try{
			//TODO: get the default album Id
			Album masterAlbum = new Album();
			masterAlbum.setTitle(DEFAULT_ALBUM_TITLE);
			masterAlbum.setUserNick(album.getUserNick());
			String masterAlbumId = this.findAlbum(masterAlbum).get(0).getAlbumId();

			List<Photo> photoList = album.getPhotos();
			//if there aren't any photos, then we don't need to move
			if (photoList != null)
			{
				for(Photo i: photoList)
				{
					System.out.println("moving to master album: "+ masterAlbumId);
					this.movePhotoToAlbum(album.getUserNick(), masterAlbumId, i);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	 * Returns the all the user albums
	 * 
	 */
	@Override
	public List<Album> findUserAlbums(String userNick) throws IllegalArgumentException{
		
		if (userNick == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		this.logger.debug("finding users albums");
		Album album = new Album();
		album.setUserNick(userNick);
		
		return this.findAlbum(album);
	}
	
	@Override
	public List<Photo> getPhotosFromAlbum(String albumId, String userNickname)
			throws IllegalArgumentException, IOException {
		if (userNickname == null || albumId == null)
		{
			this.logger.error("Some argument(s) is/are null, can't continue with the action");
			throw new IllegalArgumentException("Album argument is null, can't continue with the action");
		}
		this.logger.debug("getting all the images from \"{}\" album", albumId);
		
		Album album = this.searchAlbumUniqueUtil(albumId, userNickname);
		
		return album.getPhotos();
		
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
	Album searchAlbumUniqueUtil(String albumId, String userNickname) throws IOException
	{
		this.logger.debug("Searching for the album {} from {} user", albumId, userNickname);
		List<Album> aList = new ArrayList<Album>();
		Album a = new Album();
		
		a.setAlbumId(albumId);
		a.setUserNick(userNickname);
		
		aList = this.albumDao.findAlbum(a);
		
		if (aList.isEmpty())
		{
			this.logger.error("No {} album from {} named is stored in database", albumId, userNickname);
			throw new IOException("No " + albumId + " album from "+ userNickname +" named is stored in database");
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
