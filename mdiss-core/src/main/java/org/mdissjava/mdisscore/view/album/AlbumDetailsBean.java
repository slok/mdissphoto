package org.mdissjava.mdisscore.view.album;

import java.util.ArrayList;
import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.view.params.ParamsBean;

@RequestScoped
@ManagedBean
public class AlbumDetailsBean {
	
	private String userNick;
	private String albumTitle;
	
	private List<String> photoURLs;
	private List<String> photoTitles;
	
	public AlbumDetailsBean()
	{	
		//TODO: Replace this with DB logic!
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		this.albumTitle = "AlbumTitle";
		
		this.photoURLs = new ArrayList<String>();
		
		this.photoURLs.add("http://www.trucospc.info/fondos-de-pantalla/Naturaleza/imagenes/Snow%20Mountain.jpg");
		this.photoURLs.add("http://www.gjxu.com/uploads/Mountain-Wallpaper1.jpg");
		this.photoURLs.add("http://2.bp.blogspot.com/_Hrh98i7uFqo/TSX0BAlLewI/AAAAAAAAACg/6jIG1FSxdKU/s1600/Great+Snow+Mountain.jpg");
		this.photoURLs.add("http://www.dummyimage.com/260x260/");
		
		this.photoTitles = new ArrayList<String>();
		this.photoTitles.add("Photo 1");
		this.photoTitles.add("Photo 2");
		this.photoTitles.add("Photo 3");
		this.photoTitles.add("Photo 4");
	}
	
	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
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

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
