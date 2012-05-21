package org.mdissjava.mdisscore.view.explore;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.view.params.ParamsBean;

@RequestScoped
@ManagedBean
public class ExploreBean {
	
	private List<String> photoURLs;
	private List<String> photoTitles;
	private List<String> photoUsers;
	
	public ExploreBean()
	{	
		//TODO: Replace this with DB logic!
		
		this.photoURLs = new ArrayList<String>();
		
		this.photoURLs.add("http://www.dummyimage.com/420x380/");
		this.photoURLs.add("http://www.dummyimage.com/380x420/");
		this.photoURLs.add("http://www.dummyimage.com/420x260/");
		this.photoURLs.add("http://www.dummyimage.com/240x180/");
		this.photoURLs.add("http://www.dummyimage.com/380x300/");
		this.photoURLs.add("http://www.dummyimage.com/180x240/");
		this.photoURLs.add("http://www.dummyimage.com/500x180/");
		this.photoURLs.add("http://www.dummyimage.com/320x240/");

		
		this.photoTitles = new ArrayList<String>();
		this.photoTitles.add("Photo 1");
		this.photoTitles.add("Photo 2");
		this.photoTitles.add("Photo 3");
		this.photoTitles.add("Photo 4");
		this.photoTitles.add("Photo 5");
		this.photoTitles.add("Photo 6");
		this.photoTitles.add("Photo 7");
		this.photoTitles.add("Photo 8");
		
		this.photoUsers = new ArrayList<String>();
		this.photoUsers.add("User 1");
		this.photoUsers.add("User 2");
		this.photoUsers.add("User 4");
		this.photoUsers.add("User 4");
		this.photoUsers.add("User 3");
		this.photoUsers.add("User 1");
		this.photoUsers.add("User 2");
		this.photoUsers.add("User 4");
	}
	
	public List<String> getPhotoURLs() {
		return photoURLs;
	}

	public void setPhotoURLs(List<String> photoURLs) {
		this.photoURLs = photoURLs;
	}

	public List<String> getPhotoTitles() {
		return photoTitles;
	}

	public void setPhotoTitles(List<String> photoTitles) {
		this.photoTitles = photoTitles;
	}

	public List<String> getPhotoUsers() {
		return photoUsers;
	}

	public void setPhotoUsers(List<String> photoUsers) {
		this.photoUsers = photoUsers;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
