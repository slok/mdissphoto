package org.mdissjava.mdisscore.model.dao;

import org.mdissjava.mdisscore.model.pojo.KeyLink;

public interface KeyLinkDao {

	void insertKeyLink(KeyLink keyLink);

	KeyLink findKeyLink(String key);

	void updateKeyLink(KeyLink keyLink);

	void deleteKeyLink(KeyLink keyLink);
}
