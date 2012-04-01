package org.mdissjava.mdisscore.model.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bo.PhotoBo;
import org.mdissjava.mdisscore.model.bo.UserBo;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;

public class UserBllImpl implements UserBll {
	//MorphiaDatastoreFactory.getDatastore("test")
	private UserDao userdao =new UserDaoImpl();
	
	@Override
	public int saveUser(UserBo user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId()==0)
		{//alta de nuevo usuario
			User usuario=new User(user);
			userdao.addUser(usuario);			
			return usuario.getId();
		}
		else
		{//salvar una modificación
			
			return 0;
		}
		
	}

	@Override
	public void deleteUser(UserBo user) {
		// poner modo activo a false

	}

	@Override
	public UserBo getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBo> getFriends(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PhotoBo getPhoto(int PhotoId)
	{
		
		return null;//new PhotoBo();
	}
	
	

}
