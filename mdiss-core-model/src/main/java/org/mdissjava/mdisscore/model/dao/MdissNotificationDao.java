package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.notifications.MdissNotification;
import org.mdissjava.mdisscore.model.pojo.notifications.PhotoUploadedNotification;

public interface MdissNotificationDao {
	
	void insertMdissNotification(MdissNotification mdissNotification);
	List<MdissNotification> findMdissNotification(MdissNotification mdissNotification);
	void updateMdissNotification(MdissNotification mdissNotification);
	void deleteMdissNotification(MdissNotification mdissNotification);
	public List<MdissNotification> findUsersMdissNotifications(String userName, int limit) throws IllegalArgumentException;
	public PhotoUploadedNotification findPhotoUploadedNotifications(String userName, String photoId) throws IllegalStateException, IllegalArgumentException;

}
