package org.mdissjava.mdisscore.solr.searcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.bson.types.ObjectId;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Camera;
import org.mdissjava.mdisscore.model.pojo.Metadata;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.solr.pojo.photo;
import org.xml.sax.SAXException;

import com.google.code.morphia.Datastore;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

//public class SolrImportDataMongo3 extends AbstractSolrTestCase {
public class SolrImportDataMongo {
	private static EmbeddedSolrServer server;
	private static final String SOLR_HOME= "D:/jboss/jboss-as-7.0.2.Final/standalone/deployments/mdissphoto/solr";
	private static final String CORE_NAME = "mongo";
	private Datastore db2;
	private PhotoDao photodao;
	private static SolrQuery query;
	
	public SolrImportDataMongo() throws MalformedURLException 
    {
  	  	try {
  	  		
  	  		Mongo mongo = new Mongo("localhost",27017);
	  	  	db2 = MorphiaDatastoreFactory.getDatastore("mdissphoto");
			photodao = new PhotoDaoImpl(db2);
  	  		DB db = mongo.getDB("mdissphoto");
	  	  	File home = new File(SOLR_HOME);	
	  		File f = new File(home, "solr.xml");
		    CoreContainer container = new CoreContainer(SOLR_HOME);
			container.load(SOLR_HOME, f);
			server = new EmbeddedSolrServer(container, CORE_NAME);
			//eliminar los documentos indexados anteriormente
			server.deleteByQuery("*:*");
			server.commit();
  			init();
  			System.out.println("Names: " + db.getCollectionNames());
  	  		//objeto de la bd mongo
  	  		DBCollection collection =db.getCollection("Photo");
  	  		//obtener el conjunto de objetos
  	  		DBCursor cursor = collection.find();
  	  		System.out.println("num objetos photo: " + cursor.count());
  	  		//recorrer todos los objetos con el cursor
  	  		int cont = 0;
  	  		while(cursor.hasNext()) {
  	  			BasicDBObject record = (BasicDBObject) cursor.next();
  	  			ObjectId objectId = (ObjectId) record.get("_id");
				  	  						
				System.out.println("Id: " + objectId.toString());
				SolrInputDocument document = new SolrInputDocument();
				//Obtener el objeto Photo con su ObjectId, y actualizar todos los campos
				Photo fotoToIndex = new Photo();
				fotoToIndex.setId(new ObjectId(objectId.toString()));
				List<Photo> listaFotosEnc = photodao.findPhoto(fotoToIndex);
				if (listaFotosEnc.size() > 0){
					Photo photo = listaFotosEnc.get(0);
					document.addField("photoId", photo.getPhotoId());
					document.addField("titleFoto", photo.getTitle());
					Album album = photo.getAlbum();
					if(album != null){
						document.addField("albumId", album.getAlbumId());
						document.addField("titleAlbum", album.getTitle());
						document.addField("userNick", album.getUserNick());						
					}
					document.addField("publicPhoto", photo.getPublicPhoto());
					document.addField("tags", photo.getTags().toArray());				  							
					Metadata metadata = photo.getMetadata();
					if(metadata != null){
						Camera camera = metadata.getCamera();
						if(camera != null){
							document.addField("brand", camera.getBrand());
							document.addField("model", camera.getModel());							
						}
						document.addField("shutterSpeed", metadata.getShutterSpeed());
						document.addField("aperture", metadata.getAperture());
						document.addField("focalLength", metadata.getFocalLength());
						document.addField("ISOSpeed", metadata.getISOSpeed());
						document.addField("sensorSize", metadata.getSensorSize());
					}														
				}
				server.add(document);
				server.commit();
				cont++;
				System.out.println("cont: " + cont);
	  		}
  	  		System.out.println("Finalizada la actualizacio de todoso los campos de la photo");
	  		
  	  	} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
  	
    }
	
	public void init() {
		db2 = MorphiaDatastoreFactory.getDatastore("mdissphoto");
		photodao = new PhotoDaoImpl(db2);
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
			    System.out.println("listaFotos: " + listaFotos.size());
			    return listaFotos;
			    
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
