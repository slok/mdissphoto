package org.mdissjava.mdisscore.view.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.mdissjava.commonutils.memcached.MemcachedConnection;
import org.mdissjava.commonutils.mongo.gridfs.GridfsDataStorer;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.commonutils.utils.Utils;

import com.sun.mail.iap.ByteArray;

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
	private static final String MEMCACHED_HOST = "127.0.0.1";
	private static final int MEMCACHED_PORT = 11211;
	private static final int MEMCACHED_PERSISTENCE_SECONDS = 3600; //60 mins
	

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

        	//memcached to the action!
        	MemcachedConnection.connect(MEMCACHED_HOST, MEMCACHED_PORT);
        	MemcachedClient client = MemcachedConnection.getConnection();
        	String key = db + "_" + bucket + "_" + imageId;
        	imageBytes = (byte[])client.get(key);
        	
        	//is in memcached?
        	if (imageBytes == null){
        		// Lookup Image by ImageId in database.        		
        		GridfsDataStorer imageDAO  = new GridfsDataStorer(db, bucket);
        		try
        		{
        			baos = (ByteArrayOutputStream)imageDAO.getData(imageId);
        			client.set(key, MEMCACHED_PERSISTENCE_SECONDS, baos.toByteArray());
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
        		System.out.println("Cacheeeeeeeeeeeed: " + key);
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