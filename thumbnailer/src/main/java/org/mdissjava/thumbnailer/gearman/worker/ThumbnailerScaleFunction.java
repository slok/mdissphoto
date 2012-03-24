package org.mdissjava.thumbnailer.gearman.worker;

import org.gearman.client.GearmanJobResult;
import org.gearman.client.GearmanJobResultImpl;
import org.gearman.util.ByteUtils;
import org.gearman.worker.AbstractGearmanFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerScaleFunction extends AbstractGearmanFunction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public GearmanJobResult executeFunction() {
		String sb = new String(ByteUtils.fromUTF8Bytes((byte[]) this.data));
		
		this.logger.info("Executing Gearman Thumbnail worker function with '{}' data", sb);
		
		sb = createThumbnails(sb);
		GearmanJobResult gjr = new GearmanJobResultImpl(this.jobHandle, true, sb.getBytes(), new byte[0], new byte[0], 0, 0);
		
		return gjr;
	}

	private String createThumbnails(String imageId) {
		// TODO Auto-generated method stub
		return null;
	}

}
