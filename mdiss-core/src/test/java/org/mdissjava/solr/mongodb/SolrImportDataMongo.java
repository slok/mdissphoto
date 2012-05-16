package org.mdissjava.solr.mongodb;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.util.AbstractSolrTestCase;
import org.bson.types.ObjectId;
import org.mdissjava.commonutils.mongo.morphia.MorphiaDatastoreConnection;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.xml.sax.SAXException;


import com.google.code.morphia.Datastore;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

//public class SolrImportDataMongo extends AbstractSolrTestCase{
public class SolrImportDataMongo extends AbstractSolrTestCase {

	private MorphiaDatastoreConnection mongo;
	private EmbeddedSolrServer server;
	private static final String SOLR_HOME= "C:/jboss/jboss-as-7.0.2.Final/standalone/deployments/example/solr";
	private static final String CORE_NAME = "mongo";
	private Datastore db;
	private PhotoDao photodao;
	
	public SolrImportDataMongo() throws MalformedURLException 
    {
  	  	try {
  	  		
  	  		Mongo mongo = new Mongo("localhost",27017);
//  	  		DB db = mongo.getDB("mongoTest");
  	  		DB db = mongo.getDB("mongoDBTest");
	  	  	File home = new File(SOLR_HOME);	
	  		File f = new File(home, "solr.xml");
		    CoreContainer container = new CoreContainer(SOLR_HOME);
			container.load(SOLR_HOME, f);
			server = new EmbeddedSolrServer(container, CORE_NAME);
  			init();
  			System.out.println("Names: " + db.getCollectionNames());
  	  		//objeto de la bd mongo
  	  		DBCollection collection =db.getCollection("Photo");
  	  		//obtener el conjunto de objetos
  	  		DBCursor cursor = collection.find();
  	  		System.out.println("num objetos photo: " + cursor.count());
  	  		//recorrer todos los objetos con el cursor
  	  		while(cursor.hasNext()) {
  	  			BasicDBObject record = (BasicDBObject) cursor.next();
  	  			ObjectId objectId = (ObjectId) record.get("_id");
				  	  						
				System.out.println("Id: " + objectId.toString());
				SolrInputDocument document = new SolrInputDocument();
//				document.addField("id", objectId.toString());
				//Obtener el objeto Photo con su ObjectId, y actualizar todos los campos
				Photo fotoToIndex = new Photo();
				fotoToIndex.setId(new ObjectId(objectId.toString()));
				List<Photo> listaFotosEnc = photodao.findPhoto(fotoToIndex);
				if (listaFotosEnc.size() > 0){
					for (Photo photo : listaFotosEnc) {	  						
//						document.addField("photoId",photo.getPhotoId());
						document.addField("title", photo.getTitle());
//						document.addField("album", photo.getAlbum());
//						document.addField("publicPhoto", photo.getPublicPhoto());
//						document.addField("votes", photo.getVotes());
//						document.addField("uploadDate", photo.getUploadDate());
//						document.addField("nextPhoto", photo.getNextPhoto());
//						document.addField("backwardPhoto", photo.getBackwardPhoto());
//						document.addField("metadata", photo.getMetadata());
						document.addField("tags", photo.getTags().toString());
//						document.addField("dataId", photo.getDataId());				  							
//						document.addField("plus18", photo.getPlus18());
					}
				}
				server.add(document);
				server.commit();
//				break;  					
	  		}
  	  		System.out.println("Finalizada la actualizacio de todoso los campos de la photo");
	  		
  	  	} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
    }
	
	public void init() {
//		db = MorphiaDatastoreFactory.getDatastore("mongoTest");
		db = MorphiaDatastoreFactory.getDatastore("mongoDBTest");
		photodao = new PhotoDaoImpl(db);
	}
	
	 public void createQuery(){
	    	
		try {
			SolrQuery query = new SolrQuery();
		    query.setQuery( "*:*" );
//		    query.addSortField( "uid", SolrQuery.ORDER.asc );
		    QueryResponse rsp;
			rsp = server.query( query );
			
		    SolrDocumentList docs = rsp.getResults();
		    
		    List<photo> beans = rsp.getBeans(photo.class);
		    System.out.println("Num coincidencias: " + docs.getNumFound());
		    for (photo photo : beans) {
				System.out.println(photo.toString());
			}
		} 
		catch (SolrServerException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public String getSchemaFile() {
		// TODO Auto-generated method stub
		return "/mongo/conf/schema.xml";
	}

	@Override
	public String getSolrConfigFile() {
		// TODO Auto-generated method stub
		return "/mongo/conf/solrconfig.xml";
	}

}
