package org.mdissjava.mdisscore.model.dao;


import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.pojo.Address;
import org.mdissjava.mdisscore.model.pojo.Configuration;
import org.mdissjava.mdisscore.model.pojo.User;



public interface UserDao {

	String addUser(User user);
	void deleteUser(User user);
	void loggedIn(ObjectId id);
	User getUserById(ObjectId id);
	void modifyAddress(ObjectId id, Address address);
	void modifyConfiguration(ObjectId id, Configuration conf);
}
