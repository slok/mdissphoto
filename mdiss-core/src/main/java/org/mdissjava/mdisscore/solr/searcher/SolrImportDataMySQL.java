package org.mdissjava.mdisscore.solr.searcher;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.mdissjava.mdisscore.solr.pojo.users;

public class SolrImportDataMySQL {

	private static final String SOLR_HOME = "C:/servers/jboss7/standalone/deployments/mdissSearch/solr";
	private static final String CORE_NAME = "mysql";
	private EmbeddedSolrServer server;

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

	public void createQuery() {
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			query.addSortField("id", SolrQuery.ORDER.asc);
			QueryResponse rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			List<users> beans = rsp.getBeans(users.class);
			docs.equals(docs); // TODO: docs Needed?
			beans.equals(beans); // TODO: beans Needed?

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
