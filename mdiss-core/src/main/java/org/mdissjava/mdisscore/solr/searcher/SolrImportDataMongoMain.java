package org.mdissjava.mdisscore.solr.searcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.mdissjava.mdisscore.solr.pojo.photo;
import org.xml.sax.SAXException;

import com.google.gson.Gson;


public class SolrImportDataMongoMain {

	private static final String SOLR_HOME= "C:/jboss/jboss-as-7.0.2.Final/standalone/deployments/mdissphoto/solr";
	private static final String CORE_NAME = "mongo";
	private static EmbeddedSolrServer server;
	private static SolrQuery query;
	
	
	public static void main(String[] args){
		try {
			new SolrImportDataMongo();
//			String strValue = "\"Watch out we got a badass over here\"";
//			String strValue = "watch";
//			searchingByField("titleFoto", "like");
//			searchingByField("titleAlbum", "Comics");
			searchingByField("titleFoto", "Iris");
//			searchingByField("titleFoto", "guy");
//			searchingByField("publicPhoto", "true");
//			searchingByField("tags", "lol");
//			searchingByField("plus18", "false");
			
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}
	
	
	/*
	 * busca por todos los titulos de las fotos
	 */	
	public static List<String> searchingByField(String filterField, String searchText){
		try {
			List<String> listaFotos = null;
			
			System.out.println("SolrImportDataMongoMain.searchingByFotoTitle()");
			File home = new File(SOLR_HOME);	
			File f = new File(home, "solr.xml");
			CoreContainer container = new CoreContainer(SOLR_HOME);
			try {
				container.load(SOLR_HOME, f);
				server = new EmbeddedSolrServer(container, CORE_NAME);
				query = new SolrQuery();
				String strquery = filterField + ":" +  searchText;
				query.setQuery(strquery);
			    QueryResponse rsp;
				rsp = server.query( query, METHOD.GET );
				
			    SolrDocumentList docs = rsp.getResults();
			    List<photo> beans = rsp.getBeans(photo.class);
			    System.out.println("Num coincidencias: " + docs.getNumFound());
			    
			    listaFotos = new ArrayList<String>();
			    Gson gson = new Gson(); 		    
			    for (photo photo : beans) {		    	
					System.out.println(gson.toJson(photo));
					listaFotos.add(gson.toJson(photo));
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			} catch (SolrServerException e) {
				System.err.println("Error: SolrImportDataMongoMain.searchingByFotoTitle()" + searchText);
				e.printStackTrace();
			}
		
		return null;
	}

		
}
