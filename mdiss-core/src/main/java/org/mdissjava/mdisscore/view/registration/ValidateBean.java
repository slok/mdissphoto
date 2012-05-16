package org.mdissjava.mdisscore.view.registration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.model.dao.KeyLinkDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;

import com.google.code.morphia.Datastore;
import com.ocpsoft.pretty.faces.annotation.URLAction;

@ManagedBean(name = "validateBean")
@ViewScoped
public class ValidateBean {

	private String key;

	private boolean valid;

	@URLAction
	public void init() {
		Datastore db = MorphiaDatastoreFactory.getDatastore("test");
		System.out.println(key);
		KeyLinkDao keyLinkDao = new KeyLinkDaoImpl(db);
		valid = keyLinkDao.retrieveUserFromValidationLink(key) != -1 ? true
				: false;
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
}