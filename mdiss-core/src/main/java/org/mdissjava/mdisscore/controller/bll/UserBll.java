package org.mdissjava.mdisscore.controller.bll;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;


public interface UserBll {
	
	void saveUser(User user);
	void deleteUser(User user);
//	void loggedIn(ObjectId id);
	User getUserById(int id);
	//void modifyAddress(ObjectId id, Address address);
	//void modifyConfiguration(ObjectId id, Configuration conf);
	List<User> getFriends(int id);
	Photo  getPhoto(int IdPhoto);
	void ChangePassword(User user, String newPassword);
	
}
