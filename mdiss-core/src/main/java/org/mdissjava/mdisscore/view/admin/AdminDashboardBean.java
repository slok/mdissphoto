package org.mdissjava.mdisscore.view.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.AlbumManager;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.AlbumManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.MdissNotificationDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.MdissNotificationDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.model.pojo.notifications.FollowingNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.ReportPhotoNotification;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class AdminDashboardBean {
	private String user;
	private User userObject;
	
	private List<MdissNotification> notifications; 
	private MdissNotificationDao mdissNotificationsDao;
	private UserManager userManager;
	private AlbumManager albumManager;
	private PhotoManager photoManager;
	private Datastore datastore;
	private String thumbnailBucket = "square.150";
	private String thumbnailDatabase = "images";
	private List<Album> albums;
	private int knowMoreTimes = 1; 

	private Map<String, User> users;
	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private int NOTIFICATION_BATCH_NUMBER = 10; 
	private boolean more;
		
	private int totalUsers;
	private int totalPhotos;
	private int totalAlbums; 
	
	private List<ReportPhotoNotification> reportNotifications;
	private List<PhotoUploadedNotification> uploadNotifications;
	private List<FollowingNotification> followingNotifications;

	
	public AdminDashboardBean() throws IOException {
		this.user = getUser();
		//get properties
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
	
		//get datastores
		datastore = MorphiaDatastoreFactory.getDatastore(database);
		mdissNotificationsDao = new MdissNotificationDaoImpl(datastore);
		userManager = new UserManagerImpl();
		albumManager = new AlbumManagerImpl(datastore);
		photoManager = new PhotoManagerImpl(datastore);
		
		reportNotifications = new ArrayList<ReportPhotoNotification>();
		uploadNotifications = new ArrayList<PhotoUploadedNotification>();
		followingNotifications = new ArrayList<FollowingNotification>();
		
		//get all the notifications
		notifications = mdissNotificationsDao.findAllNotifications(NOTIFICATION_BATCH_NUMBER);
		this.users = new HashMap<String, User>();
		
		for (MdissNotification n: notifications){
			if (n instanceof ReportPhotoNotification){
				ReportPhotoNotification rn = (ReportPhotoNotification)n;
				reportNotifications.add(rn);
			}
			else if (n instanceof FollowingNotification){
				FollowingNotification fn = (FollowingNotification)n;
				followingNotifications.add(fn); 
			}
			else if (n instanceof PhotoUploadedNotification){
				PhotoUploadedNotification pn = (PhotoUploadedNotification)n;
				uploadNotifications.add(pn);				
			}
		} 		
		
		this.totalUsers = userManager.getTotalUsers();	
		this.totalPhotos = photoManager.getTotalPhotos();
		this.totalAlbums = albumManager.getTotalAlbums();				
	}

	
//	public void infiniteScroll(AjaxBehaviorEvent event){
//		this.knowMoreTimes++;
//		this.notifications = mdissNotificationsDao.findUsersMdissNotifications(this.user, NOTIFICATION_BATCH_NUMBER * knowMoreTimes);
//		if (this.notifications.size() != (NOTIFICATION_BATCH_NUMBER * knowMoreTimes)){
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"No notifications remaining", "No more Notifications"));
//			this.more = false;
//		}else
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Loaded " + NOTIFICATION_BATCH_NUMBER*knowMoreTimes + " more", null));
//	}
		
	public void deletePhoto(MdissNotification n){
		//	try {
		//		photoManager.deletePhoto(n.getPhotoId);
		//	} catch (IOException e) {
		//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Could not delete selected photo", null));
		//	}
		deleteMdissNotification(n);
	}
	
	public void deleteMdissNotification(MdissNotification n){
		mdissNotificationsDao.deleteSameMdissReportNotifications(n);
	}
	
	public String findAvatar (String userId){
		return this.userManager.getAvatar(userId);
	}
	
	
	public List<MdissNotification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<MdissNotification> notifications) {
		this.notifications = notifications;
	}

	
	public String getThumbnailBucket() {
		return thumbnailBucket;
	}

	public void setThumbnailBucket(String thumbnailBucket) {
		this.thumbnailBucket = thumbnailBucket;
	}

	public String getThumbnailDatabase() {
		return thumbnailDatabase;
	}

	public void setThumbnailDatabase(String thumbnailDatabase) {
		this.thumbnailDatabase = thumbnailDatabase;
	}
	
	public boolean isMore() {
		return more;
	}


	public void setMore(boolean more) {
		this.more = more;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public User getUserObject() {
		return userObject;
	}


	public void setUserObject(User userObject) {
		this.userObject = userObject;
	}


	public void setUser(String user) {
		this.user = user;
	}
	
	
	public String getUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public int getTotalUsers() {
		return totalUsers;
	}


	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}


	public int getTotalPhotos() {
		return totalPhotos;
	}


	public void setTotalPhotos(int totalPhotos) {
		this.totalPhotos = totalPhotos;
	}


	public int getTotalAlbums() {
		return totalAlbums;
	}


	public void setTotalAlbums(int totalAlbums) {
		this.totalAlbums = totalAlbums;
	}


	public List<ReportPhotoNotification> getReportNotifications() {
		return reportNotifications;
	}


	public void setReportNotifications(
			List<ReportPhotoNotification> reportNotifications) {
		this.reportNotifications = reportNotifications;
	}


	public List<PhotoUploadedNotification> getUploadNotifications() {
		return uploadNotifications;
	}


	public void setUploadNotifications(
			List<PhotoUploadedNotification> uploadNotifications) {
		this.uploadNotifications = uploadNotifications;
	}


	public List<FollowingNotification> getFollowingNotifications() {
		return followingNotifications;
	}


	public void setFollowingNotifications(
			List<FollowingNotification> followingNotifications) {
		this.followingNotifications = followingNotifications;
	}
		

}
