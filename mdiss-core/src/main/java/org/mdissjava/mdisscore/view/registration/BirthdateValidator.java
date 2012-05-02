package org.mdissjava.mdisscore.view.registration;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.mdissjava.commonutils.properties.PropertiesFacade;


@FacesValidator("org.mdissjava.mdisscore.view.registration.BirthdateValidator")
public class BirthdateValidator implements Validator{

	
	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MIN_USER_AGE = "min_user_age.var";
	
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Date fecha=(Date)value;
		
		String edad="18";
		try {
		 edad= new PropertiesFacade().getProperties(GLOBAL_PROPS_KEY).getProperty(MIN_USER_AGE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Date aux=new Date();
		
			aux.setYear(aux.getYear()- Integer.parseInt(edad));
		
		
		if(fecha.compareTo(aux)>0)
		{
			FacesMessage msg = 
					new FacesMessage("you must be "+edad+" years old", 
							"you must be "+edad+" years old");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
		if(fecha.after(new Date()))
		{
			FacesMessage msg = 
					new FacesMessage("Error", 
							"Future date");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
		
	}

}
