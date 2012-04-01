package org.mdissjava.mdisscore.model.bll;

import org.mdissjava.mdisscore.model.bo.UserBo;

public interface LoginBll {
	UserBo Login(String email,String password);
	boolean Logout(UserBo user);
}
