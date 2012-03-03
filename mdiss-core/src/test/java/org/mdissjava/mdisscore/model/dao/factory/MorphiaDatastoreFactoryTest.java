package org.mdissjava.mdisscore.model.dao.factory;

import org.junit.Test;

public class MorphiaDatastoreFactoryTest {

	@Test
	public void test(){
		MorphiaDatastoreFactory.getDatastore("test");
	}

}
