package org.mdissjava.mdisscore.controller.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.hibernate.HibernateUtil;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class UserManagerImpl implements UserManager {
	//MorphiaDatastoreFactory.getDatastore("test")
	private UserDao userdao =new UserDaoImpl();
	

	
	@Override
	public void saveUser(User user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId()==0)
		{//alta de nuevo usuario
			System.out.println("UserManagerImpl//Nuevo Usuario**********");
			user.setPass(PEncoder(user.getPass()));
			userdao.addUser(user);				
		}
		else
		{//salvar una modificación
			
			userdao.updateUser(user);
			
		}
		
	}

	@Override
	public void deleteUser(User user) {		
		userdao.deleteUser(user);

	}

	@Override
	public User getUserById(int id) {
		
		return userdao.getUserById(id);
	}

	@Override
	public List<User> getFriends(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public Photo getPhoto(int PhotoId)
	{
		
		return null;//new PhotoBo();
	}
	
	@Override
	public void ChangePassword(User user, String newPassword) {		
		user.setPass(PEncoder(newPassword));
		userdao.updateUser(user);		
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

	@Override
	public boolean EmailAllReadyExist(String email) {
		return	userdao.emailAllReadyExists(email);
		
	}

	@Override
	public boolean NickAllReadyExist(String nick) {
		
		return userdao.nickAllReadyExists(nick);
	}



}
