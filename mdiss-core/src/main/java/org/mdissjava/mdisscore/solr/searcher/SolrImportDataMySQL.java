package org.mdissjava.mdisscore.solr.searcher;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.mdissjava.mdisscore.solr.pojo.users;

import com.google.gson.Gson;

public class SolrImportDataMySQL {

//	private static final String SOLR_HOME = "C:/servers/jboss7/standalone/deployments/mdissSearch/solr";
	private static final String SOLR_HOME = "D:/jboss/jboss-as-7.0.2.Final/standalone/deployments/mdissphoto/solr";
	private static final String CORE_NAME = "mysql";
	private static EmbeddedSolrServer server;

	public SolrImportDataMySQL() throws MalformedURLException {
		try {
			File home = new File(SOLR_HOME);
			File f = new File(home, "solr.xml");
			CoreContainer container = new CoreContainer(SOLR_HOME);
			container.load(SOLR_HOME, f);
			server = new EmbeddedSolrServer(container, CORE_NAME);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public static List<String> searchingByField(String filterField, String searchText){
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
