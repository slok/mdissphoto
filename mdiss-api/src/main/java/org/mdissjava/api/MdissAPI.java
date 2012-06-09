package org.mdissjava.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.mdissjava.api.interceptors.HmacSecurityInterceptor;
/**
 * This is neccessary for the jboss 7 Resteasy bundled jar 
 * 
 * @author slok
 *
 */

@ApplicationPath("/api/1.0")
public class MdissAPI extends Application
{
	private Set<Class<?>> classes;
	private Set<Object> singletons;
	
	
	public MdissAPI() {
		this.classes = new HashSet<Class<?>>();
		this.singletons = new HashSet<Object>();
		
		//set classes
		this.classes.add(HmacSecurityInterceptor.class);
		this.classes.add(Albums.class);
		this.classes.add(PhotoContent.class);
		this.classes.add(Users.class);
		this.classes.add(Photos.class);
		
		//set singletons
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return this.classes;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return this.singletons;
	}
	
}