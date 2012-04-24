package org.mdissjava.mdisscore.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.dao.lucene.search.AnalyzedDBObject;
import org.mdissjava.mdisscore.model.dao.lucene.search.AnalyzedDBObject.Condition;
import org.mdissjava.mdisscore.model.dao.lucene.search.MongoDatastoreLucene;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.mongodb.DBObject;

public class PhotoDaoImplLuceneTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	Datastore db;
	PhotoDao photodao;
	static final String INDPHOTO01 ="indPhoto01";
	static final String INDEXED_FIELD = "indPhoto";
	static final String TEXT = "Fiestas de Deusto 2012 con los colegas de Leioa";
	static final String COLLECTION_NAME = "Photo";
	static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
	
	@Before
	public void init() {
		db = MorphiaDatastoreFactory.getDatastore("busquedaLucene");
		photodao = new PhotoDaoImpl(db);
	}

	@After
	public void destroy() {
		db = null;
		photodao = null;
	}

	@Test
	public void testInsertFind(MongoDatastoreLucene mongo) {

		
		this.logger.info("[TEST] testInsertField");

		Photo photo = new Photo();
		photo.setPlus18(true);
		photo.setPublicPhoto(false);
		photo.setTitle("Fiestas de Deusto 2012 con los colegas de Leioa");
		photo.setPhotoId("this is a mighty ID");
		List<String> tags = new ArrayList<String>();
		tags.add("fiestas");
		tags.add("deusto");
		tags.add("colegas");
		photo.setTags(tags);
		photo.setDataId(photo.getPhotoId());

		/* A continuacion, se crea un indice para indexar en mongo el campo "Title" del objeto "Photo"
		    * 
		    */
		//Convertir la lista de tags a un String
		String sTags = tags.get(0);
		for (int i = 1; i < tags.size(); i++) {
			sTags += "," + tags.get(i);
		}
		System.out.println("Todos tags: " + sTags);
		
	   try {
		
//		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30, new String[] {"title", "tags"} , analyzer);
//		Hits hits = searcher.Search(queryParser.parse(photo.getTitle()));
	   
		
		mongo.save(
		           new AnalyzedDBObject(analyzer)
		           .appendAndAnalyzeFullText(INDPHOTO01, sTags)
		           .append("tags", sTags)
		           .append("title", TEXT.toLowerCase()),		           
		           COLLECTION_NAME);
		
			//Buscar por titulo
			String texto = "con los colegas";
			texto = texto.toLowerCase();
			DBObject result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,texto, Condition.IN),COLLECTION_NAME);
			assertEquals(TEXT.toLowerCase(), result.get("title"));
			
			texto = "FieStas de DeuSto";
			texto = texto.toLowerCase();
			result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,texto, Condition.IN),COLLECTION_NAME);
			assertEquals(TEXT.toLowerCase(), result.get("title"));
			
			texto = "Fiesstas de Deeusto 2012";
			texto = texto.toLowerCase();
			result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,texto, Condition.IN),COLLECTION_NAME);
			assertNull(result);
			
			
			// Buscar por tags
			result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,"fiestas", Condition.IN),COLLECTION_NAME);
			//assertTrue(result.get("tags").toString().contains("fiestas"));
			assertEquals(sTags, result.get("tags"));
			
			result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,"deusto", Condition.IN),COLLECTION_NAME);
			//assertTrue(result.get("tags").toString().contains("deusto"));
			assertEquals(sTags, result.get("tags"));
			
			result = mongo.get(new AnalyzedDBObject(analyzer).createQuery(INDPHOTO01,"colegas", Condition.IN),COLLECTION_NAME);
			//assertTrue(result.get("tags").toString().contains("colegas"));
			assertEquals(sTags, result.get("tags"));

	   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}