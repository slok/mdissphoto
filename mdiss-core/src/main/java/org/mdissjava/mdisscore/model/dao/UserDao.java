package org.mdissjava.mdisscore.model.dao;


import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.pojo.User;


public interface UserDao {

	void addUser(User user); 
	
	void deleteUser(User user);
	
	void loggedIn(ObjectId id);
	
	User getUserById(int id);
	
	boolean emailAllReadyExists(String email);
	
	boolean nickAllReadyExists(String nick);
	
	User getUserByNick(String username);
	
//	void modifyAddress(ObjectId id, Address address);
	
//	void modifyConfiguration(ObjectId id, Configuration conf);
	
	void updateUser(User user);
	
	List<User> findFriends(User user);
	
	void addFriend(int userid, int friendid);
	
	void deleteFriend(int userid, int friendid);
	
//	Configuration findConfiguration(User user);
}
