package org.mdissjava.mdisscore.model.bll;

import org.mdissjava.mdisscore.model.bo.UserBo;


public interface UserBll {
	
	void addUser(UserBo user);
	void deleteUser(UserBo user);
//	void loggedIn(ObjectId id);
	UserBo getUserById(String id);
	//void modifyAddress(ObjectId id, Address address);
	//void modifyConfiguration(ObjectId id, Configuration conf);
	
	
}
