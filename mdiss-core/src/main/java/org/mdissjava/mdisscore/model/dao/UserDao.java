package org.mdissjava.mdisscore.model.dao;


import java.util.List;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.pojo.User;


public interface UserDao {

	void addUser(User user); 
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	void loggedIn(ObjectId id);
	
	User getUserById(int id);
	
	User getUserByNick(String username);
	
	boolean emailAlreadyExists(String email);
	
	boolean nickAlreadyExists(String nick);
	
//	void modifyAddress(ObjectId id, Address address);
	
//	void modifyConfiguration(ObjectId id, Configuration conf);
	
	List<User> findFollows(User user);
	
	List<User> findFollowers(User user);
	
	void addFollow(int userid, int friendid);
	
	void deleteFollow(int userid, int friendid);
	
	void addFollower(int userid, int friendid);
	
	void deleteFollower(int userid, int friendid);	

}
