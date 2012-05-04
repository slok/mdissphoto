package org.mdissjava.mdisscore.controller.auth;



import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginErrorPhaseListener implements PhaseListener
{	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7645493620518898948L;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
    public void beforePhase(final PhaseEvent arg0)
    {
		//"Filed required" error message customization

		//Access the messages.properties file (not the _es)
		String messageBundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();
		ResourceBundle messageBundle = ResourceBundle.getBundle(messageBundleName);
		
		//First we get all the Faces messages that are currently stored 
		List<FacesMessage> fmessages = FacesContext.getCurrentInstance().getMessageList();
		//Then search for those error messages with the tag IS_REQUIRED_FAIL (set in our custom messages.properties file) and customize them
		for (FacesMessage fm : fmessages)
		{	
			int i = fm.getSummary().indexOf("IS_REQUIRED_FAIL");
			if (i != -1)
			{
				/* 
				 * The error message contains IS_REQUIRED_FAIL + {0} which is substituted by JSF with 
				 * the "label" of the field that issued the error message. We need that "label" to use 
				 * the correct error message from the messages.properties file so we trim it.
				 */
				String fieldName = fm.getDetail().replaceAll("IS_REQUIRED_FAIL ", "").trim();
				//Now that we have the name we use it to create the customized error message:
				//Set summary
				fm.setSummary(messageBundle.getString("fieldRequired"));
				//Set detail
				String key = fieldName.toLowerCase()+"Empty";		
				fm.setDetail(messageBundle.getString(key));
				this.logger.error("FIELD REQUIRED: Field {} is empty", fieldName);
			}
		} 
    	
    }
 
    @Override
    public void afterPhase(final PhaseEvent arg0)
    {}
 
    @Override
    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
 
}
