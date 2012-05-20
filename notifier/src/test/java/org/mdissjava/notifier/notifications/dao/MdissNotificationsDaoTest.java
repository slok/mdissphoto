package org.mdissjava.notifier.notifications.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.notifier.notifications.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.notifier.notifications.pojo.FollowingNotification;
import org.mdissjava.notifier.notifications.pojo.MdissNotification;
import org.mdissjava.notifier.notifications.pojo.PhotoUploadedNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class MdissNotificationsDaoTest {

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Datastore db;
	private MdissNotificationDao mdissNotification;

	@Before
	public void init() {
		List<Class> clazzez = new ArrayList<Class>();
		clazzez.add(MdissNotification.class);
		clazzez.add(PhotoUploadedNotification.class);
		clazzez.add(FollowingNotification.class);
		MorphiaDatastoreConnection mdc = MorphiaDatastoreConnection.getInstance();
		mdc.connect("127.0.0.1", 27017, "test", clazzez);
		mdissNotification = new MdissNotificationDaoImpl(mdc.getDatastore());
	}

	@After
	public void destroy() {
		db = null;
		mdissNotification = null;
	}
	
	@Test
	public void insertionMdissNotificationTest() {
		
		//insert the two notifications
		PhotoUploadedNotification notification = new PhotoUploadedNotification("slok's friend", "2sa1d3sad4564dsa3f23rr4");
		notification.setRead(false);
		notification.setDate(new Date());
		notification.setSelfUserName("slok");
		
		FollowingNotification notification2 = new FollowingNotification("slok's follower");
		notification2.setRead(true);
		notification2.setDate(new Date());
		notification2.setSelfUserName("slok");
		
		this.mdissNotification.insertMdissNotification(notification);
		this.mdissNotification.insertMdissNotification(notification2);
		
		//get both notifications
		notification = new PhotoUploadedNotification(null, null);
		notification.setSelfUserName("slok");
		List<MdissNotification> notifications = this.mdissNotification.findUsersMdissNotifications("slok", 5);
		System.out.println(notifications);
	}

}
