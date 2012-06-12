package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;

public class SolrImportDataMySQLMain {
	
	public static void main(String[] args) throws MalformedURLException {
		
		SolrImportDataMySQL conex = new SolrImportDataMySQL();
		conex.createQuery();
	}

}
