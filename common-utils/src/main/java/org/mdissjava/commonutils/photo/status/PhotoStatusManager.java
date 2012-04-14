package org.mdissjava.commonutils.photo.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;

public class PhotoStatusManager {
	
	private PhotoStatusDao photoStatusDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PhotoStatusManager(Datastore datastore) {
		photoStatusDao = new PhotoStatusDaoImpl(datastore);
	}
	
	public void createPhotoStatus(String name) throws IOException
	{
		//set the basics to search 
		PhotoStatus photoStatus = new PhotoStatus();
		photoStatus.setName(name);
		
		//check if alredy exists the photo status
		List<PhotoStatus> photoStatusList = photoStatusDao.findPhotoStatus(photoStatus);
		if(!photoStatusList.isEmpty())
			throw new IOException("The object " + name + " already existis in database");
		
		//add the rest to the object to insert
		//nulll because we have 3 states (no processed = null, start processing = false, processed = true )
		photoStatus.setProcessed(null); 
		photoStatus.setDetailed(false);
		photoStatus.setUpdateDate(new Date());
		
		photoStatusDao.insertPhotoStatus(photoStatus);
		
		this.logger.info("Created the photo status");
	}
	
	public void deletePhotoStatus(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		
		photoStatusDao.deletePhotoStatus(ps);
		this.logger.info("deleted the photo status");
	}
	
	public boolean needsToBeProcessed(String name) throws IOException
	{
		boolean result = false;
		
		PhotoStatus ps = searchUniqueUtil(name);
		
		//only process if is null
		//false means that we have called gearman to process but gearman doesn't finished jet
		//this means that the work is in the job stack already
		if (ps.isProcessed() == null)
			result = true;
		
		return result;
		
	}
	
	public boolean hasStartedProcessing(String name) throws IOException
	{
		boolean result = false;
		
		PhotoStatus ps = searchUniqueUtil(name);
		if (ps.isProcessed() != null)
			result = true;
		
		return result;
	}
	
	public boolean hasFinishedProcessing(String name) throws IOException
	{
		boolean result;
		
		PhotoStatus ps = searchUniqueUtil(name);
		if (ps.isProcessed() == null || ps.isProcessed() == false) //if is null and we do only the true check then the program fails with null pointer exception
			result = false;
		else
			result = true;
		
		return result;
	}
	
	public void markAsProcessedStarted(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		ps.setProcessed(false);
		ps.setUpdateDate(new Date());
		this.photoStatusDao.updatePhotoStatus(ps);
		this.logger.info("Updated the photo status marking as process started");
	}
	
	public void markAsProcessedFinished(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		ps.setProcessed(true);
		ps.setUpdateDate(new Date());
		this.photoStatusDao.updatePhotoStatus(ps);
		this.logger.info("Updated the photo status marking as process finished");
	}
	
	public void markAsNeedsToBeProcessed(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		ps.setProcessed(null);
		ps.setUpdateDate(new Date());
		this.photoStatusDao.updatePhotoStatus(ps);
		this.logger.info("Updated the photo status marking as process needded");
	}
	
	public boolean needsToBeDetailed(String name) throws IOException
	{
		boolean result = false;
		
		PhotoStatus ps = searchUniqueUtil(name);
		
		if (ps.isDetailed() == false)
			result = true;
		
		return result;
		
	}
	
	public void markAsNeedsTobeDetailed(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		ps.setDetailed(false);
		ps.setUpdateDate(new Date());
		this.photoStatusDao.updatePhotoStatus(ps);
		this.logger.info("Updated the photo status marking as undetailed");
	}
	
	public void markAsDetailed(String name) throws IOException
	{
		PhotoStatus ps = this.searchUniqueUtil(name);
		ps.setDetailed(true);
		ps.setUpdateDate(new Date());
		this.photoStatusDao.updatePhotoStatus(ps);
		
		this.logger.info("Updated the photo status marking as detailed");
	}
	
	private PhotoStatus searchUniqueUtil(String name) throws IOException
	{
		this.logger.info("Searching for the photo with {} name", name);
		List<PhotoStatus> psList = new ArrayList<PhotoStatus>();
		PhotoStatus ps = new PhotoStatus();
		ps.setName(name);
		
		
		psList = photoStatusDao.findPhotoStatus(ps);
		
		if (psList.isEmpty())
			throw new IOException("No " + name + " photostatus named is stored in database");
	
		if (psList.size() > 1)
			throw new IllegalStateException("Too many photostatuses found, expected only one");
		
		return psList.get(0);
	}

}
