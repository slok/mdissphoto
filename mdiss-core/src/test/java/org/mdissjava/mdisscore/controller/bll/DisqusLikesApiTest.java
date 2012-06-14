package org.mdissjava.mdisscore.controller.bll;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mdissjava.mdisscore.view.photo.DisqusJsonReader;

public class DisqusLikesApiTest {
	
	final private String DISQUS_CONSUMER_KEY ="http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/7e9ff298-3e8c-4d8a-8ec5-5a0b83d57fea/";
	
/*	@Test
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
	}*/
	
	@Test
	public void callAlternateDisqusTest() throws IllegalArgumentException, IOException {
		DisqusJsonReader dqr=new DisqusJsonReader();
		int likes;
		int dislikes;
			 InputStream is = new URL("http://symplio.com/symfony2rymble/Disqus/index.php?url=http://jboss.mdiss.info/mdissphoto/u/cerealguy/photo/0d66d742-33a6-4a1a-a086-8c6848b7b088/").openStream();
			    try {
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			      String As=rd.readLine();
			      System.out.println(As);
			      String aux1="";
			      String aux2="";
			      boolean find=false;
			 for(int i=1;i<As.length()-1;i++)
				 {
				 if(As.charAt(i)=='-')
					 find=true;
				 else{if(!find)
				 			aux1+=As.charAt(i);
				 		else
				 			aux2+=As.charAt(i);
				 	}
				 }
			System.out.println("Num1:"+aux1);
			System.out.println("Num2:"+aux2);
				
		} finally {
		      is.close();
		    }
	}
}
