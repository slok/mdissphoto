package org.mdissjava.mdisscore.model.dao.factory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MorphiaDatastoreFactoryTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void MorphiaDataStoreCorrectInstanceTest(){
		System.out.println(this.getClass());
		this.logger.info("[TEST] MorphiaDataStoreCorrectInstanceTest");
		MorphiaDatastoreFactory.getDatastore("test");
	}

}
