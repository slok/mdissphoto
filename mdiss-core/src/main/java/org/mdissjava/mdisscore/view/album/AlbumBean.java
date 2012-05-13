package org.mdissjava.mdisscore.view.album;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

@RequestScoped
@ManagedBean
public class AlbumBean {
	
	private String userNick;
	private List<Album> albumList;
	
	private List<String> detailedPhotoURLs;
	private List<List<String>> albumPhotosURLs;
	
	public AlbumBean()
	{
		this.albumPhotosURLs = new ArrayList<List<String>>();
		this.detailedPhotoURLs = new ArrayList<String>();
		this.detailedPhotoURLs.add("http://www.trucospc.info/fondos-de-pantalla/Naturaleza/imagenes/Snow%20Mountain.jpg");
		this.detailedPhotoURLs.add("http://www.gjxu.com/uploads/Mountain-Wallpaper1.jpg");
		this.detailedPhotoURLs.add("http://2.bp.blogspot.com/_Hrh98i7uFqo/TSX0BAlLewI/AAAAAAAAACg/6jIG1FSxdKU/s1600/Great+Snow+Mountain.jpg");

		this.albumPhotosURLs.add(detailedPhotoURLs);
		this.albumPhotosURLs.add(detailedPhotoURLs);
		this.albumPhotosURLs.add(detailedPhotoURLs);
		this.albumPhotosURLs.add(detailedPhotoURLs);

	}

	public List<List<String>> getAlbumPhotosURLs() {
		return albumPhotosURLs;
	}



	public void setAlbumPhotosURLs(List<List<String>> albumPhotosURLs) {
		this.albumPhotosURLs = albumPhotosURLs;
	}



	public List<String> getDetailedPhotoURLs() {
		return detailedPhotoURLs;
	}

	public void setDetailedPhotoURLs(List<String> detailedPhotoURLs) {
		this.detailedPhotoURLs = detailedPhotoURLs;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public List<Album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
	}

	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}

}
