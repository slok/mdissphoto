package org.mdissjava.mdisscore.view.params;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * ParamsBean is a bean with a unique target. The ParamsBean will be used to pass 
 * parameters from one bean to an other. This is managed with Prettyfaces. The data
 * of the URLs will be set in this bean and the other beans will access to this bean
 * to get that parameter
 * 
 * @author MDISS Java team 2011-2012 University of Deusto
 *
 */

@RequestScoped
@ManagedBean
public class ParamsBean {
	
	private String userId;
	private String albumId;
	private String photoId;
	private String cameraId;
	private String top;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	
	

}
