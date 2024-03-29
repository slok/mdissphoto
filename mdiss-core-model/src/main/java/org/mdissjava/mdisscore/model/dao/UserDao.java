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
	
//		void modifyAddress(ObjectId id, Address address);
	
//		void modifyConfiguration(ObjectId id, Configuration conf);
	
	List<User> findFollows(String userId, int pageNumber, int maxResults);
	
	List<User> findFollowers(String user, int pageNumber, int maxResults);
	
	void addFollow(String userNickname, User follow);

	void addFollower(String userNickname, User follower);

	void deleteFollow(String userNickname, User follow);
	
	void deleteFollower(String userNickname, User follower);
	
	void activateUser(int userid);
	
	void deactivateUser(int userid);	

	boolean followsUser(String userNickname, User follower);
	
	User getUserByEmail(String email);

	List<User> getUserByRole(String role);

	int getTotalUsers();

	String getAvatar(String userId);

	List<User> findAllUsers(int pageNumber, int maxResults);
}
