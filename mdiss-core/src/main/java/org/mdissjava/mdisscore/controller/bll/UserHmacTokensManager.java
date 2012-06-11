package org.mdissjava.mdisscore.controller.bll;

import java.security.NoSuchAlgorithmException;

import org.mdissjava.mdisscore.model.pojo.UserHmacTokens.HmacService;

public interface UserHmacTokensManager {
	
	String getUserHmacToken(String username, HmacService service);
	void insertOrUpdateUserHmacToken(String username, HmacService service, String token);
	String insertOrUpdateUserHmacToken(String username, HmacService service) throws NoSuchAlgorithmException;
	void deleteUserHmacToken(String username, HmacService service);

}
