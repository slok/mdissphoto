package org.mdissjava.mdisscore.view.registration;

import java.util.Date;

import javax.faces.application.FacesMessage;
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

		Date fecha=(Date)value;

		Date aux=new Date();
		aux.setYear(aux.getYear()-18);
		if(fecha.compareTo(aux)>0)
		{
			FacesMessage msg = 
					new FacesMessage("Solo pueden acceder mayores de edad a la aplicación", 
							"Fecha nacimiento incorrecta");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
		if(fecha.after(new Date()))
		{
			FacesMessage msg = 
					new FacesMessage("Fecha incorrecta(Acaso crees que existe una maquina del futuro?)", 
							"Fecha nacimiento incorrecta");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
		
	}

}
