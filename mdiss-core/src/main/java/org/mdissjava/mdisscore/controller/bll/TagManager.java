package org.mdissjava.mdisscore.controller.bll;

import java.io.IOException;
import java.util.List;

import org.mdissjava.mdisscore.model.pojo.Photo;

public interface TagManager {

	List<Photo> getPhotosFromTag(String tagDescription) throws IllegalArgumentException, IOException;
		
	
}
