package org.mdissjava.thumbnailer.gearman.worker;

import java.util.ArrayList;
import java.util.List;

import javax.management.InvalidAttributeValueException;

import org.gearman.worker.GearmanFunction;


public class GearmanDaemonWorker{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws InvalidAttributeValueException
	{
		//add functions
		List<Class<GearmanFunction>> functions = new ArrayList<Class<GearmanFunction>>();
		Class gf = ThumbnailerScaleFunction.class;
		functions.add((Class<GearmanFunction>)gf);
		
		ThumbnailerGearmanWorker worker = new ThumbnailerGearmanWorker(functions);
		//start worker
		worker.start();
	}

}
