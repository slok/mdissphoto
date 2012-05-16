package org.mdissjava.solr.mongodb;

import java.net.MalformedURLException;

import org.junit.Test;


public class SolrImportDataMongoTest {

//	@Test
	public void loadData(){
		try {
			SolrImportDataMongo solr = new SolrImportDataMongo();
			solr.createQuery();
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
