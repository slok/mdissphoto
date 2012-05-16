package org.mdissjava.mdisscore.view.registration;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "validateBean")
@ViewScoped
public class ValidateBean {

	private String key;

	private boolean valid;

	@PostConstruct
	public void init() {
		valid = false;
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