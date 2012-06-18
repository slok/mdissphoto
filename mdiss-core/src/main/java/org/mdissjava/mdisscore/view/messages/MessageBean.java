package org.mdissjava.mdisscore.view.messages;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.DirectMessageDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.DirectMessageDaoImpl;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.notifier.event.manager.NotificationManager;
import org.mdissjava.notifier.event.observable.DirectMessageObservable;
import org.primefaces.event.SelectEvent;
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
	private Map<String, User> users;
	private String thumbnailDatabase;
	
	private static Logger logger = Logger
			.getLogger(MessageBean.class.getName());

	public MessageBean() {
		logger.info("MessageBean Constructor inititated");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		this.userName = auth.getName();
		UserManager um = new UserManagerImpl();
		user = um.getUserByNick(this.userName);

		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);

		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);
		
		this.thumbnailDatabase = "images";
		
		//get all the users
		UserManager userManager = new UserManagerImpl();
		this.users = new HashMap<String, User>();
		for (DirectMessage message: messages){
			String nick = message.getFromUserName();
			this.users.put(nick, userManager.getUserByNick(nick));
		}

	}

	public List<DirectMessage> getMessagesReceived() {
		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);
		for (DirectMessage message : messages) {
			if (message.getFromUserId() == user.getId()) {
				message.setFromUserName("Me");
			}
		}
		return messages;
	}

	public List<DirectMessage> getMessagesSent() {
		DirectMessage filter = new DirectMessage();
		filter.setFromUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);
		for (DirectMessage message : messages) {
			if (message.getToUserId() == user.getId()) {
				message.setToUserName("Me");
			}
		}
		return messages;
	}

	public void sendMessage() {
		DirectMessage messageToSend = new DirectMessage();
		messageToSend.setSentDate(new Date());
		messageToSend.setRead(false);
		UserManager um = new UserManagerImpl();
		messageToSend.setToUserId(um.getUserByNick(sendingUserTo).getId());
		messageToSend.setText(this.getSendingText());
		messageToSend.setFromUserId(user.getId());
		messageToSend.setFromUserName(user.getNick());
		messageToSend.setToUserName(sendingUserTo);
		directMessageDao.insertDirectMessage(messageToSend);

		MessageBean.logger.info("Direct Message Sent");
		NotificationManager notifier = NotificationManager.getInstance();
		DirectMessageObservable dmo = notifier.getDirectMessageObservable();

		dmo.messageSent(messageToSend.getFromUserName(),
				messageToSend.getText(), messageToSend.getSentDate());
		MessageBean.logger.info("Direct Message Notification sent");

		String outcome = "pretty:messages-send-confirmation";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().getNavigationHandler()
				.handleNavigation(facesContext, null, outcome);

	}

	// public List<String> complete() {
	// List<String> results = new ArrayList<String>();
	//
	// // TODO: Autocomplete of the "to" textfield. Followers? Followings? All?
	//
	// return results;
	// }

	public void onRowSelect(SelectEvent event) {

		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);
		DirectMessage selectedMessage = (DirectMessage) event.getObject();
		directMessageDao.markAsRead(selectedMessage.getId());

	}

	public void deleteRow() {
		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);
		directMessageDao.deleteDirectMessage(selectedMessage);
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

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public String getThumbnailDatabase() {
		return thumbnailDatabase;
	}

	public void setThumbnailDatabase(String thumbnailDatabase) {
		this.thumbnailDatabase = thumbnailDatabase;
	}
	

}