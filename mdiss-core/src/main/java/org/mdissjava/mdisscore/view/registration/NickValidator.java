package org.mdissjava.mdisscore.view.registration;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;


@FacesValidator("org.mdissjava.mdisscore.view.registration.NickValidator")
public class NickValidator implements Validator{

	private UserManager userBll= new UserManagerImpl();
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		if(userBll.NickAllReadyExist(value.toString()))
		{
			throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Not valid",
                "Nick already exists"));
		}
		
	}

}
