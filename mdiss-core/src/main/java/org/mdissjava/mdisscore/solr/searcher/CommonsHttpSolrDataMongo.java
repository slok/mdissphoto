package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.mdissjava.mdisscore.solr.pojo.photo;

import com.google.gson.Gson;

public class CommonsHttpSolrDataMongo {
	
	private static final String URL = "http://jboss.mdiss.info/solr/mongo";
	private static CommonsHttpSolrServer server;
	
	
	public CommonsHttpSolrDataMongo() throws MalformedURLException{
		try {
			server = new CommonsHttpSolrServer(URL);
			server.setParser(new XMLResponseParser());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * busca por todos los titulos de las fotos
	 */	
	public static List<String> searchingByField(String filterField, String searchText){
			List<String> listaFotos = null;
			
			System.out.println("SolrImportDataMongoMain.searchingByFotoTitle()");			
			SolrQuery query = new SolrQuery();
			String strquery = filterField + ":" +  searchText;
			query.setQuery(strquery);
		    QueryResponse rsp;
		    
			try {
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
			    System.out.println("listaFotos: " + listaFotos.size());
			    return listaFotos;
				
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	

}
