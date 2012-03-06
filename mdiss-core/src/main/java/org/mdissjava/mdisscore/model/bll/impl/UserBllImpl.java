package org.mdissjava.mdisscore.model.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bo.PhotoBo;
import org.mdissjava.mdisscore.model.bo.UserBo;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;

public class UserBllImpl implements UserBll {

	private UserDao userdao =new UserDaoImpl(MorphiaDatastoreFactory.getDatastore("test"));
	
	@Override
	public String saveUser(UserBo user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId().isEmpty())
		{//alta de nuevo usuario
			String id=userdao.addUser(new User(user));			
			return id;
		}
		else
		{//salvar una modificación
			
			return "";
		}
		
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

	@Override
	public List<UserBo> getFriends(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PhotoBo getPhoto(int PhotoId)
	{
		
		return null;//new PhotoBo();
	}
	
	

}
