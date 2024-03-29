package org.mdissjava.mdisscore.view.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.photo.status.PhotoStatusManager;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.metadata.impl.MetadataExtractorImpl;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

@RequestScoped
@ManagedBean
public class UploadBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String imageId;
	private String userId;
	private GridfsDataStorer gfs = null;

	private String morphiaDB;
	private String imageDB;
	private String originalImageBucket;
	private int minSize;

	private final String GLOBALS_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private final String IMAGES_DATABASE_KEY = "images.db";
	private final String ORIGINAL_IMG_BUCKET_KEY = "images.bucket";
	private final String UPLOAD_MIN_SIZE = "image.min.resolution";

	public UploadBean() {
		try {
			ParamsBean pb = getPrettyfacesParams();
			this.userId = pb.getUserId();

			Properties globals;
			globals = new PropertiesFacade().getProperties(GLOBALS_PROPS_KEY);
			morphiaDB = globals.getProperty(MORPHIA_DATABASE_KEY);
			imageDB = globals.getProperty(IMAGES_DATABASE_KEY);
			originalImageBucket = globals.getProperty(ORIGINAL_IMG_BUCKET_KEY);
			minSize = Integer.valueOf(globals.getProperty(UPLOAD_MIN_SIZE));

			gfs = new GridfsDataStorer(imageDB, originalImageBucket);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		String outcome = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			// Save in database
			this.logger.info("Entering the upload");
			InputStream originalData = event.getFile().getInputstream();

			this.imageId = gfs.saveData(originalData);
			this.logger.info("upload and stored: {}", this.imageId);

			// the inputStream is empty we need to retrieve again
			ByteArrayOutputStream data = (ByteArrayOutputStream) gfs
					.getData(this.imageId);

			// get metadata
			Metadata metadata = new MetadataExtractorImpl()
					.obtenerMetadata(data.toByteArray());

			if ((metadata.getResolutionREAL().getWidth() < minSize)
					|| ((metadata).getResolutionREAL().getHeight() < minSize)) {

				gfs.deleteData(this.imageId);
				outcome = "pretty:upload-error";
			} else {

				// get the datastore ans save metadata
				Datastore ds = MorphiaDatastoreFactory
						.getDatastore(this.morphiaDB);
				new PhotoManagerImpl(ds).insertMetadata(imageId, metadata);

				// set the new status to the photo
				PhotoStatusManager photoManager = new PhotoStatusManager(ds);
				photoManager.createPhotoStatus(this.imageId);

				// set the paramas in the url
				ParamsBean params = getPrettyfacesParams();
				params.setPhotoId(this.imageId);
				params.setUserId(this.userId);

				outcome = "pretty:upload-processing";
			}

		} catch (Exception e) {
			System.out.println(e);
			outcome = "pretty:upload-error";

		} finally {
			// call
			facesContext.getApplication().getNavigationHandler()
					.handleNavigation(facesContext, null, outcome);
		}

	}

	private ParamsBean getPrettyfacesParams() {
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication()
				.evaluateExpressionGet(context, "#{paramsBean}",
						ParamsBean.class);
		return pb;
	}

}
