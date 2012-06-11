package org.mdissjava.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.HttpRequest;
import org.mdissjava.api.helpers.ApiHelper;
import org.mdissjava.commonutils.properties.PropertiesFacade;
import org.mdissjava.mdisscore.model.dao.AlbumDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.AlbumDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Album;
import org.mdissjava.mdisscore.model.pojo.User;

import com.google.code.morphia.Datastore;

@Path("/users")
public class Users {

	private final String GLOBAL_PROPS_KEY = "globals";
	private final String MORPHIA_DATABASE_KEY = "morphia.db";
	private Datastore datastore = null;
	private String userName = null;
	
	public Users(@Context HttpRequest request) throws IllegalArgumentException, IOException {
		this.userName = ApiHelper.getUserFromHttpRequest(request);
		PropertiesFacade propertiesFacade = new PropertiesFacade();
		String database = propertiesFacade.getProperties(GLOBAL_PROPS_KEY).getProperty(MORPHIA_DATABASE_KEY);
		this.datastore = MorphiaDatastoreFactory.getDatastore(database);
	
	}
	
	@GET
	@Path("/{username}/following")
	@Produces("application/json")
	public Response getUserFollowings(@PathParam("username") String username){
		
		User user = new User();		
		UserDao userDao = new UserDaoImpl();
		
		user = userDao.getUserByNick(username);
		
		//System.out.println(user.getFollows());
		
		if (!user.equals(null))
		{		
			List<User> following = user.getFollows();
			return Response.status(200).entity(following).build();
		}
		else
			return Response.status(400).entity("Error retrieving user's followings").build();
	}
	
	@GET
	@Path("/{username}/followers")
	@Produces("application/json")
	public Response getUserFollowers(@PathParam("username") String username){
		
		User user = new User();		
		UserDao userDao = new UserDaoImpl();
		
		user = userDao.getUserByNick(username);
		
		//System.out.println(user.getFollowers());
		
		if (!user.equals(null))
		{
			List<User> followers = user.getFollowers();
			return Response.status(200).entity(followers).build();
		}
		else
			return Response.status(400).entity("Error retrieving user's followers").build();
	}
	
	@GET
	@Path("/{username}")
	@Produces("application/json")
	public Response getUser(@PathParam("username") String username){
		
		User user = new User();		
		UserDao userDao = new UserDaoImpl();

		user = userDao.getUserByNick(username);
		
		if (!user.equals(null))			
			return Response.status(200).entity(user).build();
		else
			return Response.status(400).entity("Error retrieving user").build();
	}
	
	/*
	@PUT
	@Path("/{username}")
	@Consumes("application/json")
	public Response updateUser(@PathParam("username") String username, User user){
	
	}
	*/
	/*
	@POST
	@Consumes("application/json")
	public Response createUser(User user){
		
		//Test: curl -i -X POST -H Accept:application/json -H Content-Type:application/json  -d '{"title":"Me","userNick":"horl"}' '127.0.0.1:8080/mdissapi/api/1.0/albums/'
		if (user != null){
			
			UserDao userDao = new UserDaoImpl();
			
			//search if the username or email already exists in DB
			if (!userDao.getUserByNick(user.getNick()).equals(null))
				return Response.status(400).entity("Username is taken").build();
			if (!userDao.getUserByEmail(user.getEmail()).equals(null))
				return Response.status(400).entity("Email already exists").build();
			
			//save in database
			userDao.addUser(user);
			return Response.status(200).entity("User asuccessfully created: " + user).build();
			
		}else
			return Response.status(400).entity("Error creating user").build();
	}
	*/
	@GET
	@Path("/{username}/albums")
	@Produces("application/json")
	public Response getUserAlbums(@PathParam("username") String username){
		
		User user = new User();		
		UserDao userDao = new UserDaoImpl();
		
		Album album = new Album();
		album.setUserNick(username);
		
		user = userDao.getUserByNick(username);
		
		AlbumDao albumDao = new AlbumDaoImpl(this.datastore);
		
		if (!user.equals(null))
		{
			List<Album> albums = albumDao.findAlbum(album);
			if (albums.size() > 0)
				return Response.status(200).entity(albums).build();
			else
				return Response.status(200).entity("User has no albums").build();
		}
		else
			return Response.status(400).entity("Error user not found").build();
	}
}
