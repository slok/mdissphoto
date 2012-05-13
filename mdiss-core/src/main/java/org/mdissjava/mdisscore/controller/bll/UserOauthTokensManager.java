package org.mdissjava.mdisscore.controller.bll;

import org.mdissjava.mdisscore.model.pojo.OauthAccessToken;
import org.mdissjava.mdisscore.model.pojo.UserOauthTokens.Service;

public interface UserOauthTokensManager {
	
	OauthAccessToken getUserOauthAccessToken(String username, Service service);
	void insertOrUpdateUserOauthAccessToken(String username, Service service, String token, String tokenSecret);
	void deleteUserOauthAccessToken(String username, Service service);
}
