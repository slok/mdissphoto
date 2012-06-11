package org.mdissjava.mdisscore.model.dao;

import java.util.List;

import org.mdissjava.mdisscore.model.pojo.UserHmacTokens;

public interface UserHmacTokensDao {

	void insertUserHmacTokens(UserHmacTokens userHmacTokens);
	List<UserHmacTokens> findUserHmacTokens(UserHmacTokens userHmacTokens);
	void updateUserHmacTokens(UserHmacTokens userHmacTokens);
	void deleteUserHmacTokens(UserHmacTokens userHmacTokens);
}
