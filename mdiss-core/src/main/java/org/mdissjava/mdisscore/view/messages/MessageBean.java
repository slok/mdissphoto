package org.mdissjava.mdisscore.view.messages;

import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.model.dao.DirectMessageDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.DirectMessageDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@ManagedBean(name = "messageBean")
@ViewScoped
public class MessageBean {

	private String userName;
	private Datastore db;
	private DirectMessageDao directMessageDao;
	private List<DirectMessage> messages;
	public DirectMessage selectedMessage;

	private static Logger logger = Logger
			.getLogger(MessageBean.class.getName());

	public MessageBean() {
		ParamsBean pb = getPrettyfacesParams();
		this.userName = pb.getUserId();
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUserByNick(userName);

		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);

		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		messages = directMessageDao.findDirectMessage(filter, true);

	}

	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication()
				.evaluateExpressionGet(context, "#{paramsBean}",
						ParamsBean.class);
		return pb;
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

}