package org.mdissjava.mdisscore.controller.bll;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;


public interface UserManager {
		
	void saveUser(User user);
	void deleteUser(User user);
//	void loggedIn(ObjectId id);
	User getUserById(int id);
	User getUserByNick(String nick);
	//void modifyAddress(ObjectId id, Address address);
	//void modifyConfiguration(ObjectId id, Configuration conf);
	List<User> findFollows(String userId);
	List<User> findFollowers(String user);
	Photo  getPhoto(int IdPhoto);
	void changePassword(User user, String newPassword);
	boolean emailAlreadyExists(String email);
	boolean nickAlreadyExists(String nick);
	public void addFollow(String nick);
	public void activateUser(int idUser);
	
	
}
	
