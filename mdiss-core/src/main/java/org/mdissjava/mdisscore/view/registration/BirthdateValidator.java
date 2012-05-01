package org.mdissjava.mdisscore.view.registration;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("org.mdissjava.mdisscore.view.registration.BirthdateValidator")
public class BirthdateValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

}
