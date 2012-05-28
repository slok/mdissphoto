package org.mdissjava.mdisscore.view.registration;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.KeyLink;
import org.mdissjava.mdisscore.model.pojo.User;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.mdissjava.notifier.event.manager.NotificationManager;
import org.mdissjava.notifier.event.observable.VerifyAccountObservable;

import com.google.code.morphia.Datastore;

@ManagedBean(name = "validateBean")
@ViewScoped
public class ValidateBean {

	private String key;
	private UserDao userDao = new UserDaoImpl();
	private boolean valid;
	private String email;

	private static Logger logger = Logger.getLogger(RegistrationBean.class
			.getName());

	@PostConstruct
	public void init() {
		key = this.getPrettyfacesParams().getToken();
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		KeyLinkDao keyLinkDao = new KeyLinkDaoImpl(db);
		int userId = 0;
		try {
			userId = keyLinkDao.retrieveUserFromValidationLink(key);
			valid = userId != -1 ? true : false;
		} catch (IllegalArgumentException e) {
			// This means the key doesn't exist in the database.
			// If the key exists and is invalid the method returns -1
			valid = false;
		}

		if (valid) {
			// TODO: Activate User using the userId
			userDao.activateUser(userId);
		}
	}

	public void resend(ActionEvent actionEvent) throws ValidatorException {

		ValidateBean.logger.info("Validation Email resend");

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + userDao
				+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		User user = userDao.getUserByEmail(this.email);
		if (user != null && !user.isActive()) {
			valid = true;
			Datastore db = MorphiaDatastoreFactory.getDatastore("test");
			KeyLinkDao keyLinkDao = new KeyLinkDaoImpl(db);
			KeyLink keylink = new KeyLink(user.getId(),
					KeyLink.EMAIL_VALIDATION);
			keyLinkDao.insertKeyLink(keylink);
			NotificationManager notifier = NotificationManager.getInstance();
			VerifyAccountObservable vao = notifier.getVerifyAccountObservable();

			vao.userRegister(
					user.getName() + " " + user.getSurname(),
					user.getEmail(),
					"http://localhost:8080/mdissphoto/p/validate/"
							+ keylink.getId());
		} else {
			valid = false;
		}
		String outcome = "pretty:validateconfirmation";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().getNavigationHandler()
				.handleNavigation(facesContext, null, outcome);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;

	}

	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication()
				.evaluateExpressionGet(context, "#{paramsBean}",
						ParamsBean.class);
		return pb;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}