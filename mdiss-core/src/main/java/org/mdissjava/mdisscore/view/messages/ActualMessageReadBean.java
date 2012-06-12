package org.mdissjava.mdisscore.view.messages;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;
import org.mdissjava.mdisscore.model.dao.DirectMessageDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.DirectMessageDaoImpl;
import org.mdissjava.mdisscore.model.pojo.DirectMessage;
import org.mdissjava.mdisscore.model.pojo.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.code.morphia.Datastore;

@ManagedBean(name = "actualMessageReadBean")
@ViewScoped
public class ActualMessageReadBean {

	private Datastore db;
	private DirectMessageDao directMessageDao;
	private User user;
	private String userName;

	public ActualMessageReadBean() {
		db = MorphiaDatastoreFactory.getDatastore("test");
		directMessageDao = new DirectMessageDaoImpl(db);
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		this.userName = auth.getName();
		UserManager um = new UserManagerImpl();
		user = um.getUserByNick(this.userName);
	}

	public int getNumber() {
		DirectMessage filter = new DirectMessage();
		filter.setToUserId(user.getId());
		filter.setRead(false);
		List<DirectMessage> messages = directMessageDao.findDirectMessage(
				filter, false);
		return messages.size();
	}

}
