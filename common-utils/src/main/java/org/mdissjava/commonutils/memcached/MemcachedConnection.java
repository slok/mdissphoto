package org.mdissjava.commonutils.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class MemcachedConnection {

	
	static private MemcachedConnection instance = null;
	static private MemcachedClient connection = null;
	static private String host;
	static private int port;
	
	private MemcachedConnection(String host, int port) throws IOException {
		MemcachedConnection.host = host;
		MemcachedConnection.port = port;
		MemcachedConnection.connection = new  MemcachedClient(
			    new InetSocketAddress(host, port));
	}
	
	public static MemcachedConnection connect(String host, int port) throws IOException{
		if( (MemcachedConnection.host != host) ||
				(MemcachedConnection.port != port) ||
				(MemcachedConnection.instance == null) ||
				(MemcachedConnection.connection == null)){
			
			instance = new MemcachedConnection(host, port);
		
		}
		return instance;
		
	}
	
	public static void disconnect(){
		connection = null;
	}

	public static MemcachedClient getConnection() {
		if (connection == null)
			throw new IllegalStateException("Not instance created");
		return connection;
	}	
}
