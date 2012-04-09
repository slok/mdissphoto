package org.mdissjava.thumbnailer.gearman.client;

import java.util.concurrent.atomic.AtomicBoolean;

import org.gearman.client.GearmanIOEventListener;
import org.gearman.common.GearmanPacket;
import org.gearman.common.GearmanPacketType;
import org.gearman.util.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailerScaleListener implements GearmanIOEventListener{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private AtomicBoolean finishedFlag;
	
	
	public ThumbnailerScaleListener(AtomicBoolean finishedFlag) {
		this.finishedFlag = finishedFlag;
		
	}
	@Override
	public void handleGearmanIOEvent(GearmanPacket gearmanPacket){
		
		
		GearmanPacketType packetType = gearmanPacket.getPacketType();
		
		this.logger.info("Packet from Gearman received: {}", packetType.toString());
		
		//Convert bytes to string
		String result = ByteUtils.fromUTF8Bytes(gearmanPacket.getData());
		
		//Check the type of event that Gearman is submitting us
		if (packetType == GearmanPacketType.WORK_COMPLETE)
		{	
			//set the finished flag
			this.finishedFlag.set(true);
			
			this.logger.info("Gearman worker completed with: {}", result);
			
		}if (packetType == GearmanPacketType.WORK_FAIL)
		{
			this.logger.info("Gearman worker failed with: {}", result);
		}
		
	}

}
