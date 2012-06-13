package org.mdissjava.api.interceptors;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.mdissjava.api.helpers.ApiHelper;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;


@Provider
@ServerInterceptor
public class ModificationSecurityInterceptor implements PreProcessInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String DATABASE =  "mdissphoto";
	
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod)
			throws Failure, WebApplicationException {
		
		Datastore datastore = MorphiaDatastoreFactory.getDatastore(DATABASE);
		
		//get neccesary data
		Map<String, List<String>> headers = request.getHttpHeaders().getRequestHeaders();
			
		this.logger.info("Executing Modification Security Interceptor");
	
		String method = request.getHttpMethod();
		String user = headers.get(ApiHelper.HEADER_KEY_USER).get(0);
		URI uri = request.getUri().getAbsolutePath();
		
		if (method.equals("PUT") || method.equals("DELETE"))
		{

			/*  The URLs follow a fixed pattern:
			 * 
			 * 			/mdissapi/api/{version}/keyword/{value}/[keyword2]/[{value}]/
			 * 
			 *  [ ] => Optional
			 * 
			 *  Therefore, we know that the values are in the 4th and 6th position of 
			 *  the URL starting from the word /mdissapi/ (being this last 0 position). 
			 *  
			 */
			
			String path = uri.getPath().toString();
			System.out.println(path);
			String[] uriData = path.substring(1, path.length()-1).split("/");
			
			Map<String,String> hm = new HashMap<String,String>();
			
			//1st set of keyword/value
			String keyword1 = uriData[3];
			String value1 = uriData[4];
			hm.put(keyword1, value1);
			
			if(uriData.length > 5)
			{
				//2nd set of keyword/value
				String keyword2 = uriData[5];
				String value2 = uriData[6];
				hm.put(keyword2, value2);					
			}
			
			Iterator it = hm.entrySet().iterator();

			while (it.hasNext()) 
			{
				Map.Entry e = (Map.Entry)it.next();
				
				System.out.println(e.getValue());
				
				//TODO: Reflection instead of a simple "if else" to do this? 
				
				if (e.getKey().toString().toLowerCase().indexOf("album") != -1)
				{
					Album album = new Album();
					album.setAlbumId(e.getValue().toString());
					
					AlbumDao albumDao = new AlbumDaoImpl(datastore);
					List<Album> albums = albumDao.findAlbum(album);
					
					if(albums.size() == 1)
					{
						if(!albums.get(0).getUserNick().equals(user))
						{
							logger.error("[ERROR] " + method + " not allowed: Object " + e.getValue() + " is not " + user + " user's");
							return (ServerResponse) Response.status(400).entity("Error:  Object " + e.getValue() + " is not " + user + " user's").build();
						}
					}			
				}
				else if (e.getKey().toString().toLowerCase().indexOf("photo") != -1)
				{
					Photo photo = new Photo();
					photo.setPhotoId(e.getValue().toString());
					
					PhotoDao photoDao = new PhotoDaoImpl(datastore);
					List<Photo> photos = photoDao.findPhoto(photo);
					
					if(photos.size() == 1) 
					{
						if (!photos.get(0).getAlbum().getUserNick().equals(user))
						{
							logger.error("[ERROR] " + method + " not allowed: Object " + e.getValue() + " is not " + user + " user's");
							return (ServerResponse) Response.status(400).entity("Error:  Object " + e.getValue() + " is not " + user + " user's").build();
						}	
					}
				}
				
				return null;
				
			}
		}
		
		return null;
	}

}
