package org.mdissjava.mdisscore.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.utils.Utils;

public class ImageDataBO {

	
	
	public InputStream getImageData(String imageId) throws IOException
	{
		GridfsDataStorer gds = new GridfsDataStorer("thumbnails", "original");
		ByteArrayOutputStream baos;
		
		baos = (ByteArrayOutputStream)gds.getData(imageId);
		ByteArrayInputStream dbStream = Utils.byteArrayOutputStreamToByteArrayInputStream(baos);
		
		return dbStream;
	}

}



