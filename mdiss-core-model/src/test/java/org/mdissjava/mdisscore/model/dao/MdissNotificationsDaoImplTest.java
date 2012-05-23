package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.pojo.notifications.FollowingNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdissNotificationsDaoImplTest {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MdissNotificationDao mdissNotification;

	@Before
	public void init() {
		List<Class> classes = new ArrayList<Class>();
		classes.add(MdissNotification.class);
		mdissNotification = new MdissNotificationDaoImpl(MorphiaDatastoreFactory.getDatastore("test", classes));
	}

	@After
	public void destroy() {
		mdissNotification = null;
	}
	
	@Test
	public void insertionMdissNotificationTest() {
		
		this.logger.info("[TEST] insertionMdissNotificationTest");
		
		//insert the two notifications
		PhotoUploadedNotification notification = new PhotoUploadedNotification("slok", UUID.randomUUID().toString());
		notification.setRead(false);
		notification.setDate(new Date());
		notification.setSelfUserName("cerealguy");
		
		FollowingNotification notification2 = new FollowingNotification("slok");
		notification2.setRead(true);
		notification2.setDate(new Date());
		notification2.setSelfUserName("cerealguy");
		
		this.mdissNotification.insertMdissNotification(notification);
		this.mdissNotification.insertMdissNotification(notification2);
		
		//get both notification types and check the data
		List<MdissNotification> notifications = this.mdissNotification.findUsersMdissNotifications("cerealguy", 5);
		
		for (MdissNotification i: notifications)
		{
			if (i instanceof PhotoUploadedNotification)
			{
				PhotoUploadedNotification iHelper = (PhotoUploadedNotification)i;
				assertEquals(notification.getSelfUserName(), iHelper.getSelfUserName());
				assertEquals(notification.getPhotoId(), iHelper.getPhotoId());
				assertEquals(notification.getUploaderUsername(), iHelper.getUploaderUsername());
			}else if  (i instanceof FollowingNotification){
				FollowingNotification iHelper = (FollowingNotification)i;
				assertEquals(notification2.getSelfUserName(), iHelper.getSelfUserName());
				assertEquals(notification2.getFollowerUserName(), iHelper.getFollowerUserName());
			}
			
			this.mdissNotification.deleteMdissNotification(i);
		}
	}
	
	@Test
	public void updateMdissNotificationTest() {
		
		this.logger.info("[TEST] updateMdissNotificationTest");
		
		//insert the two notifications
		String id = UUID.randomUUID().toString();
		PhotoUploadedNotification notification = new PhotoUploadedNotification("slok", id);
		notification.setRead(false);
		notification.setDate(new Date());
		notification.setSelfUserName("cerealguy");
		
		this.mdissNotification.insertMdissNotification(notification);
		
		//check before update
		
		PhotoUploadedNotification notificationChecker = this.mdissNotification.findPhotoUploadedNotifications("cerealguy", id);
		assertEquals(notification.getSelfUserName(), notificationChecker.getSelfUserName());
		assertFalse(notificationChecker.isRead());
		
		//update
		notificationChecker.setRead(true);
		this.mdissNotification.updateMdissNotification(notificationChecker);
		
		//check after update
		
		notificationChecker = this.mdissNotification.findPhotoUploadedNotifications("cerealguy", id);		
		assertTrue(notificationChecker.isRead());
		
		this.mdissNotification.deleteMdissNotification(notificationChecker);
	}
}
