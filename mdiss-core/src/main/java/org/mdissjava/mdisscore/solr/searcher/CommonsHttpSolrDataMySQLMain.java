package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;

public class CommonsHttpSolrDataMySQLMain {
	
	public static void main(String[] args) throws MalformedURLException {
		
		new CommonsHttpSolrDataMySQL();
		CommonsHttpSolrDataMySQL.searchingByField("nick", "user");
	}

}
