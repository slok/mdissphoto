package org.mdissjava.mdisscore.controller.bll.impl;

import java.util.List;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class UserManagerImpl implements UserManager {
	//MorphiaDatastoreFactory.getDatastore("test")
	private UserDao userDao = new UserDaoImpl();
	

	
	@Override
	public void saveUser(User user) {
		//si es nuevo usuario poner fecha del registro, y guardar
		if(user.getId()==0)
		{//alta de nuevo usuario
			System.out.println("UserManagerImpl//Nuevo Usuario**********");
			user.setPass(PEncoder(user.getPass()));
			userDao.addUser(user);				
		}
		else
		{//salvar una modificación
			
			userDao.updateUser(user);
			
		}
		
	}

	@Override
	public void deleteUser(User user) {		
		userDao.deleteUser(user);
	}

	@Override
	public User getUserById(int id) {		
		return userDao.getUserById(id);
	}

	@Override
	public List<User> findFollows(String user) {
		return userDao.findFollows(user);	
	}
	
	@Override
	public List<User> findFollowers(String user) {
		return userDao.findFollowers(user);	
	}
		
	@Override
	public Photo getPhoto(int PhotoId) {		
		return null;//new PhotoBo();
	}
	
	@Override
	public void changePassword(User user, String newPassword) {		
		user.setPass(PEncoder(newPassword));
		userDao.updateUser(user);		
	}
	
	/**
	 * Password hashing function sha-256
	 * @param password
	 * 			the orignal password
	 * @return string
	 * 			the hash code
	 */
	 
	private String PEncoder(String password) {
		PasswordEncoder sha256Encoder = new ShaPasswordEncoder(256);
		 return sha256Encoder.encodePassword(password,null);
	}

	@Override
	public boolean emailAlreadyExists(String email) {
		return	userDao.emailAlreadyExists(email);		
	}

	@Override
	public boolean nickAlreadyExists(String nick) {
		
		return userDao.nickAlreadyExists(nick);
	}

	public User getUserByNick(String nick){
		return userDao.getUserByNick(nick);
	}

	@Override
	public void addFollow(String nick) {
		// TODO Auto-generated method stub
		
	}
		


}
