package org.mdissjava.mdisscore.view.messages;

import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.model.dao.DirectMessageDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.DirectMessageDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@ManagedBean(name = "messageBean")
@ViewScoped
public class MessageBean {

	private String userName;
	private User user;
	private Datastore db;
	private DirectMessageDao directMessageDao;
	private List<DirectMessage> messages;
	private DirectMessage selectedMessage;
	private String sendingText;
	private String sendingUserTo;
	private String sendingSubject;

	private static Logger logger = Logger
			.getLogger(MessageBean.class.getName());

	public MessageBean() {
		logger.info("MessageBean Constructor inititated");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		this.userName = auth.getName();
		UserDao userDao = new UserDaoImpl();
		user = userDao.getUserByNick(userName);

		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);

		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);

	}

	public List<DirectMessage> getMessagesReceived() {
		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);
		return messages;
	}

	public List<DirectMessage> getMessagesSent() {
		DirectMessage filter = new DirectMessage();
		filter.setFromUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);
		return messages;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Datastore getDb() {
		return db;
	}

	public void setDb(Datastore db) {
		this.db = db;
	}

	public DirectMessageDao getDirectMessageDao() {
		return directMessageDao;
	}

	public void setDirectMessageDao(DirectMessageDao directMessageDao) {
		this.directMessageDao = directMessageDao;
	}

	public List<DirectMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<DirectMessage> messages) {
		this.messages = messages;
	}

	public DirectMessage getSelectedMessage() {
		return selectedMessage;
	}

	public void setSelectedMessage(DirectMessage selectedMessage) {
		this.selectedMessage = selectedMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSendingText() {
		return sendingText;
	}

	public void setSendingText(String sendingText) {
		this.sendingText = sendingText;
	}

	public String getSendingUserTo() {
		return sendingUserTo;
	}

	public void setSendingUserTo(String sendingUserTo) {
		this.sendingUserTo = sendingUserTo;
	}

	public String getSendingSubject() {
		return sendingSubject;
	}

	public void setSendingSubject(String sendingSubject) {
		this.sendingSubject = sendingSubject;
	}

}