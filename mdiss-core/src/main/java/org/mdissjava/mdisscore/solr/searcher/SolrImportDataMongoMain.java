package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;


public class SolrImportDataMongoMain {
	
	
	public static void main(String[] args){
		try {
			new SolrImportDataMongo();
//			String strValue = "\"Watch out we got a badass over here\"";
//			String strValue = "watch";
//			searchingByField("titleFoto", "like");
//			searchingByField("titleAlbum", "Comics");
//			searchingByField("titleFoto", "Iris");
			SolrImportDataMongo.searchingByField("titleFoto", "guy");
//			searchingByField("publicPhoto", "true");
//			searchingByField("tags", "lol");
//			searchingByField("plus18", "false");
			
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}

		
}
