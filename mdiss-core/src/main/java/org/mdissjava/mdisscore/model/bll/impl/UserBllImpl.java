package org.mdissjava.mdisscore.model.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.model.bll.UserBll;
import org.mdissjava.mdisscore.model.bo.PhotoBo;
import org.mdissjava.mdisscore.model.bo.UserBo;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class UserBllImpl implements UserBll {
	//MorphiaDatastoreFactory.getDatastore("test")
	private UserDao userdao =new UserDaoImpl();
	
	@Override
	public int saveUser(UserBo user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId()==0)
		{//alta de nuevo usuario
			User usuario=new User(user);
			usuario.setPass(PEncoder(user.getPassword()));
			userdao.addUser(usuario);			
			return usuario.getId();
		}
		else
		{//salvar una modificación
			User usuario=userdao.getUserById(user.getId());
			usuario.SetUserBoData(user);
			userdao.updateUser(usuario);
			return usuario.getId();
		}
		
	}

	@Override
	public void deleteUser(UserBo userbo) {
		User user=userdao.getUserById(userbo.getId());
		userdao.deleteUser(user);

	}

	@Override
	public UserBo getUserById(int id) {
		
		return new UserBo(userdao.getUserById(id));
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
	
	@Override
	public void ChangePassword(UserBo user, String newPassword) {
		User usuario=userdao.getUserById(user.getId());
		usuario.setPass(PEncoder(newPassword));
		userdao.updateUser(usuario);		
	}
	
	/**
	 * Password hashing function sha-256
	 * @param password
	 * 			the orignal password
	 * @return string
	 * 			the hash code
	 */
	 
	private String PEncoder(String password)
	{
		PasswordEncoder sha256Encoder = new ShaPasswordEncoder(256);
		 return sha256Encoder.encodePassword(password,null);
	}



}
