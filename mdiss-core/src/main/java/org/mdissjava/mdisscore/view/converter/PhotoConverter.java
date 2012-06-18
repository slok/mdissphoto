package org.mdissjava.mdisscore.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;

public class PhotoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("Converting");
		 if (arg2.trim().equals("")) {  
	            return null;  
	        } else {  
	        	PhotoManager manager = new PhotoManagerImpl(MorphiaDatastoreFactory.getDatastore("mdissphoto"));
	            Photo p = new Photo();
	            p.setPhotoId(arg2);
	            p = manager.findPhoto(p).get(0);
	            return p;
	        }  
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {  
            return "";  
        } else {
        	System.out.println("hoooorl: " + ((Photo) arg2).getPhotoId());
            return String.valueOf(((Photo) arg2).getPhotoId());  
        }  
	}

}
