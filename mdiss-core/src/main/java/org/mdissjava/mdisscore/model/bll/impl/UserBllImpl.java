package org.mdissjava.mdisscore.model.bll.impl;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bo.UserBo;
import org.mdissjava.mdisscore.model.dao.UserDao;

public class UserBllImpl implements UserBll {

	private UserDao userdao ;
	
	@Override
	public void addUser(UserBo user) {
		//si es nuevo usuario poner fecha del registro

	}

	@Override
	public void deleteUser(UserBo user) {
		// poner modo activo a false

	}

	@Override
	public UserBo getUserById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
