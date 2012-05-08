package org.mdissjava.mdisscore.view.registration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final String NICK_PATTERN = "^[_A-Za-z0-9-]+(\\." +"[_A-Za-z0-9-]+)";
	private Pattern pattern;
	private Matcher matcher;
	
	private UserManager userBll= new UserManagerImpl();
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
	/*	matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			
			FacesMessage msg = 
				new FacesMessage("Nick validation failed.", 
						"Only letters and numbers are admited");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}*/
		
		if(userBll.nickAlreadyExists(value.toString()))
		{
			throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Not valid",
                "Nick already exists"));
		}
		
	}

}
