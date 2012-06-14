package org.mdissjava.mdisscore.solr.searcher;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.mdissjava.mdisscore.solr.pojo.users;

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
	
	public void createQuery()
	{
		try
		{
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			QueryResponse rsp = server.query(query);
			List<users> user = rsp.getBeans(users.class);
			
			Iterator iter = user.iterator();
			while (iter.hasNext())
			  System.out.println(iter.next());
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
