package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.UserOauthTokens;


public interface UserOauthTokensDao {
	
	void insertUserOauthTokens(UserOauthTokens userOauthTokens);
	List<UserOauthTokens> findUserOauthTokens(UserOauthTokens userOauthTokens);
	void updateUserOauthTokens(UserOauthTokens userOauthTokens);
	void deleteUserOauthTokens(UserOauthTokens userOauthTokens);

}
