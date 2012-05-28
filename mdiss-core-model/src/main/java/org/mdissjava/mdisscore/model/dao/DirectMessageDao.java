package org.mdissjava.mdisscore.model.dao;

import java.util.Date;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.DirectMessage;

public interface DirectMessageDao {
	
	void insertDirectMessage(DirectMessage directMessage);
	
	void updateDirectMessage(DirectMessage directMessage);
	
	void deleteDirectMessage(DirectMessage directMessage);
	
	List<DirectMessage> findDirectMessage(DirectMessage directMessage);
	
	List<DirectMessage> findDirectMessageBetween(Date from, Date to);

}
