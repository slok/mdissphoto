package org.mdissjava.mdisscore.model.dao.lucene.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.util.Version;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mdissjava.mdisscore.model.dao.PhotoDaoImplLuceneTest;
import org.mdissjava.mdisscore.model.dao.PhotoDaoImplTest;
import org.mdissjava.mdisscore.model.dao.lucene.search.AnalyzedDBObject.Condition;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class IndexPhotoLuceneTest {

//	   static final String TEXT = "Fiestas de Bilbao 2012";
//	   static final String COLLECTION_NAME = "Album";
//	   static final String INDEXED_FIELD = "indTitle";
//	   static final String TEXT_FIELD = "title";
	   static MongoDatastoreLucene mongo;
//	   static final String INDPHOTO01 ="indPhoto01";

	   

	   @BeforeClass
	   public static void setup() throws UnknownHostException, MongoException {
	       mongo = new MongoDatastoreLucene();
	   }

	   @Test
	   public void verifySaveAndGetTestSearchWithAll() throws IOException {
		   
		   PhotoDaoImplLuceneTest photoDao = new PhotoDaoImplLuceneTest();
		   
		   photoDao.testInsertFind(mongo);

	   }


}
