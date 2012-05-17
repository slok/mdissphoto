package org.mdissjava.mdisscore.view.registration;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;

@ManagedBean(name = "validateBean")
@ViewScoped
public class ValidateBean {

	private String key;

	private boolean valid;

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
		}
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
}