package org.mdissjava.mdisscore.model.dao.factory;

import java.io.IOException;

import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;

public class MorphiaDatastoreFactoryTest {

	@Test
	public void test() throws IOException, ClassNotFoundException {
		MorphiaDatastoreFactory.getDatastore("test");
	}

}
