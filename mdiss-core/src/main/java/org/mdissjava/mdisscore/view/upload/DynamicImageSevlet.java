package org.mdissjava.mdisscore.view.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.mdissjava.commonutils.memcached.MemcachedConnection;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.commonutils.utils.Utils;

public class DynamicImageSevlet extends HttpServlet
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private static final String MORPHIA_DATABASE = "mdissphoto"; 
	private static String IMAGE_NOT_FOUND;
	private static final String MEMCACHED_ACTIVE_KEY = "memcached.active";
	private static final String MEMCACHED_HOST_KEY = "memcached.host";
	private static final String MEMCACHED_PORT_KEY = "memcached.port";
	private static final String MEMCACHED_PERSISTENCE_SECONDS_KEY = "memcached.expire";

    // Actions ------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	ByteArrayOutputStream baos = null;
    	ByteArrayInputStream bais = null;
    	byte[] imageBytes = null;
    	
        // Get ID from request.
        String imageId = request.getParameter("id");
        String db = request.getParameter("db");
        String bucket = request.getParameter("bucket");
        
        //Datastore datastore = MorphiaDatastoreFactory.getDatastore(MORPHIA_DATABASE);
        
        //get the necessary data to check if the user is able to load the image
        //get the photo owner
        
        /*PhotoManager photoManager = new PhotoManagerImpl(datastore);
        Photo photo = photoManager.searchPhotoUniqueUtil(imageId);
        String photoOwnerUsername = photo.getAlbum().getUserNick();
        UserManager userManager = new UserManagerImpl();
        User photoOwner = userManager.getUserByNick(photoOwnerUsername);*/
        
        //get the logged user
        //String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        
        boolean isPrivate = false; //photoOwner.getConfiguration().isIsPrivate();
        boolean isFollowing = false; //userManager.followsUser(loggedUser, photoOwner);
        boolean same = false; //photoOwnerUsername.equals(loggedUser);
        
        //memcached data
        Properties p = new PropertiesFacade().getProperties("globals");
        String memcachedActive = p.getProperty(MEMCACHED_ACTIVE_KEY);
        String memcachedHost = p.getProperty(MEMCACHED_HOST_KEY);
        String memcachedPort = p.getProperty(MEMCACHED_PORT_KEY);
        String memcachedExpire = p.getProperty(MEMCACHED_PERSISTENCE_SECONDS_KEY);
        
        
        if(isPrivate && !isFollowing && !same){
        	baos = null;
        }else{
        	
        	// Check if ID is supplied to the request.
        	if (imageId == null) {
        		// Do your thing if the ID is not supplied to the request.
        		// Throw an exception, or send 404, or show default/warning image, or just ignore it.
        		response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
        		return;
        	}

        	//memcached to the action! (maybe)
        	MemcachedClient client = null;
        	String key = null;
        	if (memcachedActive.equals("true"))
        	{
	        	MemcachedConnection.connect(memcachedHost,Integer.valueOf(memcachedPort));
	        	client = MemcachedConnection.getConnection();
	        	key = db + "_" + bucket + "_" + imageId;
	        	imageBytes = (byte[])client.get(key);
        	}
        	
        	//is in memcached?
        	if (imageBytes == null){
        		// Lookup Image by ImageId in database.        		
        		GridfsDataStorer imageDAO  = new GridfsDataStorer(db, bucket);
        		try
        		{
        			baos = (ByteArrayOutputStream)imageDAO.getData(imageId);
        			if (memcachedActive.equals("true"))
        				client.set(key, Integer.valueOf(memcachedExpire), baos.toByteArray());
        		}catch(IOException e){
        			//TODO: Memcache this!
        			IMAGE_NOT_FOUND =  new PropertiesFacade().getProperties("globals").getProperty("no.image.available");
        			baos = (ByteArrayOutputStream)imageDAO.getData(IMAGE_NOT_FOUND);
        		}
        		
        		//the final object
        		bais = Utils.byteArrayOutputStreamToByteArrayInputStream(baos);
        		
        	}else
        	{
        		bais = new ByteArrayInputStream(imageBytes);
        		System.out.println("Cached: " + key);
        	}
        }
        
        
        // Check if image is actually retrieved from database.
        if (bais == null) {
            // Do your thing if the image does not exist in database.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("image/jpeg");
        //depend on what method we have used then the size is in one or the other
        int size = baos != null?baos.size():imageBytes.length;
        response.setHeader("Content-Length", String.valueOf(size));
        response.setHeader("Content-Disposition", "inline; filename=\"" + imageId + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(bais, DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}