package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;

public class CommonsHttpSolrDataMongoMain {
	
	public static void main(String[] args) throws MalformedURLException {
		
		CommonsHttpSolrDataMongo conex = new CommonsHttpSolrDataMongo();
		conex.searchingByField("titleFoto", "Iris");
	}

}
