package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;

public class CommonsHttpSolrDataMongoMain {
	
	public static void main(String[] args) throws MalformedURLException {
		
		new CommonsHttpSolrDataMongo();
		CommonsHttpSolrDataMongo.searchingByField("titleFoto", "Iris");
	}

}
