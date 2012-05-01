package org.mdissjava.mdisscore.view.registration;
import java.util.regex.*;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.mdissjava.mdisscore.controller.bll.UserManager;
import org.mdissjava.mdisscore.controller.bll.impl.UserManagerImpl;

@FacesValidator("org.mdissjava.mdisscore.view.registration.EmailValidator")
public class EmailValidator implements Validator{

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;
	private UserManager userBll= new UserManagerImpl();
	
	public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			
			FacesMessage msg = 
				new FacesMessage("E-mail validation failed.", 
						"Invalid E-mail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}else{
			
			if(userBll.EmailAllReadyExist(value.toString()))
			{
				throw new ValidatorException(new FacesMessage(
	                FacesMessage.SEVERITY_ERROR, "Not valid",
	                "Email ya existe"));
			}
			
		}
		

	}

}
