package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.mdissjava.mdisscore.solr.pojo.users;

import com.google.gson.Gson;

public class CommonsHttpSolrDataMySQL {
	
	private static final String URL = "http://jboss.mdiss.info/solr/mysql";
	private CommonsHttpSolrServer server;
	
	
	public CommonsHttpSolrDataMySQL() throws MalformedURLException{
		try {
			server = new CommonsHttpSolrServer(URL);
			server.setParser(new XMLResponseParser());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public List<String> searchingByField(String filterField, String searchText){
		List<String> listaUsers = null;
		
		System.out.println("CommonsHttpSolrDataMySQL.searchingByField()");			
		SolrQuery query = new SolrQuery();
		String strquery = filterField + ":" +  searchText;
		query.setQuery(strquery);
	    QueryResponse rsp;
	    
		try {
			rsp = server.query(query, METHOD.GET);				
		    SolrDocumentList docs = rsp.getResults();
		    List<users> beans = rsp.getBeans(users.class);
		    System.out.println("Num coincidencias: " + docs.getNumFound());
		    
		    listaUsers = new ArrayList<String>();
		    Gson gson = new Gson(); 		    
		    for (users user : beans) {		    	
				System.out.println(gson.toJson(user));
				listaUsers.add(gson.toJson(user));
			}
		    System.out.println("listaUsuarios: " + listaUsers.size());
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
}
	

}
