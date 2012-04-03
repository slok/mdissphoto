package org.mdissjava.mdisscore.view.upload;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClient;
import org.mdissjava.thumbnailer.gearman.client.ThumbnailerGearmanClientPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ViewScoped
@ManagedBean
public class UploadProcessingBean {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ThumbnailerGearmanClient thumbClient = null;
	private String imageId = null;
	private String userId = null;
	
	
	public UploadProcessingBean() throws InterruptedException {
		logger.info("Creating Upload processor instance");
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);	
		this.imageId = pb.getPhotoId();
		this.userId = pb.getUserId();
		
	}
	
	public void init(AjaxBehaviorEvent event)
	{
		//TODO: check processed image in the DAO
		
		//set the paramas in the url
		ParamsBean params = getPrettyfacesParams();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String outcome = null;
		params.setPhotoId(this.imageId);
		params.setUserId(this.userId);
		
		try {			
			//create the connection with gearman
			this.thumbClient = (ThumbnailerGearmanClient) ThumbnailerGearmanClientPool.getInstance().getClient();
			//wait to process...
			thumbClient.ThumbnailizeImageSynchronous(this.imageId);
			
			//select where to go from here
			logger.info("Thumbnailed: {}", this.imageId);
			outcome = "pretty:user_upload_details";
			
			//free the connection (No need, the pool will do  it for us)
			
			//TODO: Processed done
		
		} catch (Exception e) {
			logger.error("Error processing image: {}, rollback image...", this.imageId);
			GridfsDataStorer gfs = new GridfsDataStorer("thumbnails", "original");
			try {
				gfs.deleteData(imageId);
				logger.info("Rollback done for image: {}", this.imageId);
				outcome = "pretty:user_upload_error"; // Do your thing?
			} catch (IOException e1) {
				logger.error("Error in the rollback of {}", this.imageId);
			}
			
		}finally
		{
			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
		}
		
		
	}
	
	public String toDetails() {
		
		System.out.println("horlhorlhorlhorl");
		ParamsBean params = getPrettyfacesParams();
		params.setPhotoId(this.imageId);
		params.setUserId(this.userId);
		
		//close the connection!!
		//FIXME: IF WE SHUTDOWN THE CLIENT JOB ISN'T DONE... USE A CONNETCION POOL :( 
		//this.thumbClient.shutdown();
		
		
		String outcome = "pretty:user_upload_details";
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
		return outcome;
	}	
	
	
	public String getImageId() {
		return this.imageId;
	}	
	
	private ParamsBean getPrettyfacesParams()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ParamsBean pb = (ParamsBean) context.getApplication().evaluateExpressionGet(context, "#{paramsBean}", ParamsBean.class);
		return pb;
	}
}
