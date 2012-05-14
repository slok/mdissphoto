package org.mdissjava.mdisscore.view.album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections.map.HashedMap;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

@RequestScoped
@ManagedBean
public class AlbumBean {
	
	private String userNick;
	
	private List<List<String>> albumPhotosURLs;
	private List<String> photoURLs;
	private List<String> albumTitles;
	
	public AlbumBean()
	{	
		this.photoURLs = new ArrayList<String>();
		
		this.photoURLs.add("http://www.trucospc.info/fondos-de-pantalla/Naturaleza/imagenes/Snow%20Mountain.jpg");
		this.photoURLs.add("http://www.gjxu.com/uploads/Mountain-Wallpaper1.jpg");
		this.photoURLs.add("http://2.bp.blogspot.com/_Hrh98i7uFqo/TSX0BAlLewI/AAAAAAAAACg/6jIG1FSxdKU/s1600/Great+Snow+Mountain.jpg");
		this.photoURLs.add("http://www.dummyimage.com/200x200/");
		
		this.albumTitles = new ArrayList<String>();
		this.albumPhotosURLs = new ArrayList<List<String>>();
		
		this.albumTitles.add("Album 1");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 2");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 3");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 4");
		this.albumPhotosURLs.add(photoURLs);
		this.albumTitles.add("Album 5");
		this.albumPhotosURLs.add(photoURLs);
	}

	public List<List<String>> getAlbumPhotosURLs() {
		return albumPhotosURLs;
	}

	public void setAlbumPhotosURLs(List<List<String>> albumPhotosURLs) {
		this.albumPhotosURLs = albumPhotosURLs;
	}

	public List<String> getAlbumTitles() {
		return albumTitles;
	}

	public void setAlbumTitles(List<String> albumTitles) {
		this.albumTitles = albumTitles;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
