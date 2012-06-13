package org.mdissjava.mdisscore.controller.bll;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Test;
import org.mdissjava.mdisscore.view.photo.DisqusJsonReader;

public class DisqusLikesApiTest {
	
	final private String DISQUS_CONSUMER_KEY ="http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/7e9ff298-3e8c-4d8a-8ec5-5a0b83d57fea/";
	
	@Test
	public void callDisqusTest() throws IllegalArgumentException, IOException {
		DisqusJsonReader dqr=new DisqusJsonReader();
		int likes = 0,dislikes;
		try {
			ArrayList<Integer> arrayAux=dqr.readLikesAndDislikes(DISQUS_CONSUMER_KEY);
			likes=arrayAux.get(0);
			dislikes=arrayAux.get(1);
			System.out.println("Numero de likes: "+likes+" /Dislikes: "+dislikes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		assertTrue(likes==1);
		
		
	}
	
}
