package org.mdissjava.mdisscore.controller.bll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class AlbumManagerTest {

	private AlbumManager albumManager;
	private PhotoManager photoManager;
	private Datastore ds = null;
	private final String ALBUM_NAME = "Summer 2099";
	private String ALBUM_ID;
	private final String USER_NICK = "Predator #16";
	private final String PHOTO_TITLE_1 = "Me smashing an aliens head :D";
	private final String PHOTO_TITLE_2 = "Oh noes! aliens :(";
	private final String PHOTO_ID_1 = "123456789";
	private final String PHOTO_ID_2 = "987654321";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void init()
	{
		this.logger.info("[TEST] init");
		ds = MorphiaDatastoreFactory.getDatastore("test");
		this.albumManager = new AlbumManagerImpl(ds);
		this.photoManager = new PhotoManagerImpl(ds);
	}
	
	@After
	public void destroy() throws IOException {
		this.logger.info("[TEST] destroy");
		try
		{
			albumManager.deleteAlbum(ALBUM_ID, USER_NICK);
			photoManager.deletePhoto(PHOTO_ID_1);
			photoManager.deletePhoto(PHOTO_ID_2);
			this.albumManager = null;
			this.photoManager = null;
		}catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void creationTest() throws IllegalArgumentException, IOException {
		
		this.logger.info("[TEST] creationTest");
		//using this insert we check both insert forms because this calls the other
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		
		//create an album to search the previous inserted one
		Album a = new Album();
		a.setAlbumId(ALBUM_ID);
		a.setUserNick(USER_NICK);
		
		assertTrue(this.albumManager.findAlbum(a).size() > 0);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void creationNullTest() throws IllegalArgumentException, IOException {
		this.logger.info("[TEST] creationNullTest");
		//to delete in the destroy
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		
		//this one to test
		this.albumManager.insertAlbum(null, USER_NICK);
		
	}
	
	@Test(expected=IOException.class)
	public void creationDuplicateTest() throws IllegalArgumentException, IOException {
		this.logger.info("[TEST] creationDuplicateTest");
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		
	}
	
	@Test
	public void addNewPhotoToAlbumTest() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] addNewPhotoToAlbumTest");
		//create album and store
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		
		//create photo
		Photo p = new Photo();
		p.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		
		//insert the photo in the album
		this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p);
		
		// check the photos
		Album a = new Album();
		a.setAlbumId(ALBUM_ID);
		a.setUserNick(USER_NICK);
		Album album = this.albumManager.findAlbum(a).get(0);
		
		assertEquals(album.getPhotos().get(0).getTitle(), PHOTO_TITLE_1);
		
		//IMPORTANT: before deleting the photo we need to delete it from the album
		album.setPhotos(new ArrayList<Photo>());
		this.albumManager.updateAlbum(album);
		
		p.setAlbum(null);
		this.photoManager.updatePhoto(p);
		
		this.photoManager.deletePhoto(p);
		
	}
	
	@Test(expected=IOException.class)
	public void addDuplicateNewPhotoTest() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] allDuplicateNewPhotoTest");
		//insert a photo
		//create photo
		Photo p = new Photo();
		p.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		
		new PhotoDaoImpl(ds).insertPhoto(p);
		
		//create album and store
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		
		//create photo
		Photo p2 = new Photo();
		p2.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		
		//insert the photo in the album
		try
		{
			this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p2);
		}catch(IOException ioe)
		{
			//delete and throw again for the test expected
			this.photoManager.deletePhoto(p);
			throw new IOException(ioe.toString());
		}
	}
	
	@Test
	public void movePhotoTest() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] movePhotoTest");
		//create 2 albums
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		String ALBUM_ID2 = this.albumManager.insertAlbum(ALBUM_NAME+"_2", USER_NICK);
		
		//create photo
		Photo p = new Photo();
		p.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		
		//insert photo to the first album
		this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p);
		
		// check the photos that is in the first album and not in the second
		//first
		Album a = new Album();
		a.setAlbumId(ALBUM_ID);
		a.setUserNick(USER_NICK);
		Album album = this.albumManager.findAlbum(a).get(0);
		assertEquals(album.getPhotos().get(0).getTitle(), PHOTO_TITLE_1);
		
		//second
		Album a2 = new Album();
		a2.setAlbumId(ALBUM_ID2);
		a2.setUserNick(USER_NICK);
		Album album2 = this.albumManager.findAlbum(a2).get(0);
		assertTrue(album2.getPhotos().isEmpty());
		
		//Move
		albumManager.movePhotoToAlbum(USER_NICK, ALBUM_ID2, PHOTO_ID_1);
		
		// check the photos that is in the second album and not in the first
		//first
		a = new Album();
		a.setAlbumId(ALBUM_ID2);
		a.setUserNick(USER_NICK);
		album = this.albumManager.findAlbum(a).get(0);
		assertEquals(album.getPhotos().get(0).getTitle(), PHOTO_TITLE_1);
		
		//second
		a2 = new Album();
		a2.setAlbumId(ALBUM_ID);
		a2.setUserNick(USER_NICK);
		album2 = this.albumManager.findAlbum(a2).get(0);
		assertTrue(album2.getPhotos().isEmpty());
		
		//delete all
		this.photoManager.deletePhoto(PHOTO_ID_1);
		this.albumManager.deleteAlbum(ALBUM_ID2, USER_NICK);
		
	}
	
	@Test(expected=IOException.class)
	public void moveMissingPhotoTest() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] moveMissingPhotoTest");
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		System.out.println("horl: "+this.ALBUM_ID);
		albumManager.movePhotoToAlbum(USER_NICK, ALBUM_ID, PHOTO_ID_1);
	}
	
	@Test(expected=IOException.class)
	public void movePhotoToMissingAlbumTest() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] movePhotoToMissingAlbumTest");
		//create photo
		Photo p = new Photo();
		p.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		this.ALBUM_ID = "2345678";
		
		//insert photo to the first album
		this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p);
		
		albumManager.movePhotoToAlbum(USER_NICK, ALBUM_ID+"32321", PHOTO_TITLE_1);
	}
	
	@Test
	public void deleteAlbum() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] deleteAlbum");
		//Create an album
		this.ALBUM_ID = this.albumManager.insertAlbum(ALBUM_NAME, USER_NICK);
		//Create de default album
		String masterId = this.albumManager.insertAlbum("Master", USER_NICK);
		System.out.println(this.ALBUM_ID);
		System.out.println(masterId);
		//create 2 photos and 
		Photo p = new Photo();
		p.setTitle(PHOTO_TITLE_1);
		p.setPhotoId(PHOTO_ID_1);
		p.setDataId(PHOTO_ID_1);
		
		Photo p2 = new Photo();
		p2.setTitle(PHOTO_TITLE_2);
		p2.setPhotoId(PHOTO_ID_2);
		p2.setDataId(PHOTO_ID_2);
		
		//Insert in the album
		this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p);
		this.albumManager.addNewPhotoToAlbum(USER_NICK, ALBUM_ID, p2);
		
		//start the checks with the deletion
		//create a album for searching. There is one, so the return quantity can't be 0
		Album album = new Album();
		album.setAlbumId(ALBUM_ID);
		album.setUserNick(USER_NICK);
		assertFalse(this.albumManager.findAlbum(album).isEmpty());
		
		//both have the same album assigned
		assertEquals(ALBUM_NAME, p.getAlbum().getTitle());
		assertEquals(ALBUM_NAME, p2.getAlbum().getTitle());
		
		//delete
		this.albumManager.deleteAlbum(ALBUM_ID, USER_NICK);
		
		//check if the album isn't in database
		assertTrue(this.albumManager.findAlbum(album).isEmpty());
		
		//check both photos if they have Master(default) their albums
		PhotoManager photoManager =  new PhotoManagerImpl(ds);
		
		p = new Photo();
		p.setPhotoId(PHOTO_ID_1);
		assertEquals(photoManager.findPhoto(p).get(0).getAlbum().getTitle(), "Master");
		
		p = new Photo();
		p.setPhotoId(PHOTO_ID_2);
		assertEquals(photoManager.findPhoto(p).get(0).getAlbum().getTitle(), "Master");
		
		this.albumManager.deleteUserAllAlbumsAndPhotos(USER_NICK);
		//this.photoManager.deletePhoto(PHOTO_2);
		//this.albumManager.deleteAlbum("Master", USER_NICK);
		
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deleteDefaultAlbum() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] deleteDefaultAlbum");
		final String DEFAULT_ALBUM_NAME = "Master";
		this.ALBUM_ID = this.albumManager.insertAlbum(DEFAULT_ALBUM_NAME, USER_NICK);
				
		//delete
		try
		{
			this.albumManager.deleteAlbum(ALBUM_ID, USER_NICK);
		}catch (IllegalArgumentException iae) {
			//now force the deletion
			this.albumManager.deleteUserAllAlbumsAndPhotos(USER_NICK);
			throw new IllegalArgumentException(iae.toString());
		}
	}
	
	@Test(expected=IOException.class)
	public void deleteMissingAlbum() throws IllegalArgumentException, IOException
	{
		this.logger.info("[TEST] deleteMissingAlbum");
		//delete album that doesn't exist
		this.albumManager.deleteAlbum("1234565432", USER_NICK);
	}
	
}
