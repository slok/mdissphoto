package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;

public class CommonsHttpSolrDataMySQLMain {
	
	public static void main(String[] args) throws MalformedURLException {
		
		CommonsHttpSolrDataMySQL conex = new CommonsHttpSolrDataMySQL();
		conex.createQuery();
	}

}
