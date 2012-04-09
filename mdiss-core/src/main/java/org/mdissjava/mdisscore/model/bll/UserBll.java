package org.mdissjava.mdisscore.model.bll;

import java.util.List;

import org.mdissjava.mdisscore.model.bo.PhotoBo;
import org.mdissjava.mdisscore.model.bo.UserBo;


public interface UserBll {
	
	int saveUser(UserBo user);
	void deleteUser(UserBo user);
//	void loggedIn(ObjectId id);
	UserBo getUserById(int id);
	//void modifyAddress(ObjectId id, Address address);
	//void modifyConfiguration(ObjectId id, Configuration conf);
	List<UserBo> getFriends(int id);
	PhotoBo  getPhoto(int IdPhoto);
	void ChangePassword(UserBo user, String newPassword);
	
}
