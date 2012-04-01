package org.mdissjava.mdisscore.model.bll.impl;

import org.mdissjava.mdisscore.model.bll.LoginBll;
import org.mdissjava.mdisscore.model.bo.UserBo;

public class LoginBllImpl implements LoginBll {
	/**
	 * Login.
	 * @param email
	 *            the user email
	 * @param password 
	 * 				the password           
	 * @return 
	 * 		return de UserBo if is ok, or null if is incorrect
	 */
	@Override
	public UserBo Login(String email, String password)
	{
		
		
		return null;
	}
	
	/**
	 * Close the session. 
	 * @param user
	 *            the user.
	 *  @return true          
	 */
	@Override
	public boolean Logout(UserBo user)
	{
		//Falta borrar datos usuario de la cache
		return user.CloseSession();			
	}

}
