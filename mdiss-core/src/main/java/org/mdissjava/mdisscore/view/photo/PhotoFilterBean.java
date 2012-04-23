package org.mdissjava.mdisscore.view.photo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;

import com.google.code.morphia.Datastore;


@RequestScoped
@ManagedBean
public class PhotoFilterBean {
	
	private Photo photo;
	private String userNick;
	private String detailedPhotoURL;
	private String modifiedImageData;

	
	public PhotoFilterBean() {
		
		ParamsBean pb = getPrettyfacesParams();
		this.userNick = pb.getUserId();
		
		try {
			//get morphia database from properties and load the photo by its id
			String database;
			PropertiesFacade propertiesFacade = new PropertiesFacade();
			database = propertiesFacade.getProperties("globals").getProperty("morphia.db");
			
			Datastore datastore = MorphiaDatastoreFactory.getDatastore(database);
			PhotoManagerImpl photoManager = new PhotoManagerImpl(datastore);
			
			this.photo = photoManager.searchPhotoUniqueUtil(pb.getPhotoId());
			
			String bucket = propertiesFacade.getProperties("globals").getProperty("images.bucket");
			database = propertiesFacade.getProperties("globals").getProperty("images.db");
			
			this.detailedPhotoURL = "/dynamic/image?db="+database+"&amp;bucket="+bucket+"&amp;id="+this.photo.getPhotoId();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void saveModifiedImageData()
	{
		try {
			//Delete the imageToDataURL format (http://es.wikipedia.org/wiki/Data:_URL) for the web browsers
			this.modifiedImageData = this.modifiedImageData.replace("data:image/png;base64,", "");
			
			//convert from base64 to bytes
			byte[] modifiedImagesBytes = DatatypeConverter.parseBase64Binary(this.modifiedImageData);
			
			//create an image
	        InputStream in = new ByteArrayInputStream(modifiedImagesBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
		
			//save in database
			ImageIO.write(bImageFromConvert, "png", new File("/home/slok/Desktop/prueba.png"));
			
						//<!--  <h:inputHidden name="modifiedImageDataField" id="modifiedImageDataField" value="#{photoFilterBean.modifiedImageData}" />
						//<h:commandButton id="formSubmitButton" styleClass="btn btn-primary btn-mini" value="Export" type="submit" action="#{photoFilterBean.saveModifiedImageData}" />-->
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Photo getPhoto() {
		return photo;
	}



	public void setPhoto(Photo photo) {
		this.photo = photo;
	}



	public String getUserNick() {
		return userNick;
	}



	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}



	public String getDetailedPhotoURL() {
		return detailedPhotoURL;
	}



	public void setDetailedPhotoURL(String detailedPhotoURL) {
		this.detailedPhotoURL = detailedPhotoURL;
	}


	

	public String getModifiedImageData() {
		return modifiedImageData;
	}



	public void setModifiedImageData(String modifiedImageData) {
		this.modifiedImageData = modifiedImageData;
	}



	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
	
}
