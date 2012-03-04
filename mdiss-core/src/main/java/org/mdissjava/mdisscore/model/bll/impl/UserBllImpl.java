package org.mdissjava.mdisscore.model.bll.impl;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bo.UserBo;
import org.mdissjava.mdisscore.model.dao.UserDao;

public class UserBllImpl implements UserBll {

	private UserDao userdao ;
	
	@Override
	public String saveUser(UserBo user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId().isEmpty())
		{//alta de nuevo usuario
	
			
			return "id del usuario";
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
	
	

}
