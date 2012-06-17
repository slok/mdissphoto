package org.mdissjava.commonutils.memcached;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.UUID;

import net.spy.memcached.MemcachedClient;

import org.junit.Test;


public class MemcachedConnectionExample {

	private String host = "127.0.0.1";
	private int port = 11211;
	
	@Test(expected=IllegalStateException.class)
	public void getWrongConnection() {
		MemcachedConnection.getConnection();
	}
	
	@Test
	public void getConnection() throws IOException{
		MemcachedConnection.connect(host, port);
		MemcachedConnection.getConnection();
	}
	
	@Test
	public void getCachedObject() throws IOException{
		MemcachedConnection.connect(host, port);
		MemcachedClient client = MemcachedConnection.getConnection();
		
		//set in memcached
		String key = UUID.randomUUID().toString();
		String content = "slok_" + UUID.randomUUID().toString();
		int seconds = 2;
		client.set(key, seconds, content);
		
		//get from memcached and check
		String result = (String) client.get(key);
		assertEquals(content, result);
		
	}
	
	
	@Test
	public void getWrongCachedObject() throws IOException{
		MemcachedConnection.connect(host, port);
		MemcachedClient client = MemcachedConnection.getConnection();
		
		//set in memcached
		String key = UUID.randomUUID().toString();
		String content = "slok_" + UUID.randomUUID().toString();
		int seconds = 2;
		client.set(key, seconds, content);
		
		//get from memcached and check
		String result = (String) client.get("notAKey");
		assertNull(result);
	}
	
	@Test
	public void getExpiredCachedObject() throws IOException, InterruptedException{
		MemcachedConnection.connect(host, port);
		MemcachedClient client = MemcachedConnection.getConnection();
		
		//set in memcached
		String key = UUID.randomUUID().toString();
		String content = "slok_" + UUID.randomUUID().toString();
		int seconds = 1;
		client.set(key, seconds, content);
		
		Thread.sleep(1001);
		
		//get from memcached and check
		String result = (String) client.get(key);
		assertNull(result);
	}
	
	@Test
	public void getNotExpiredCachedObject() throws IOException, InterruptedException{
		MemcachedConnection.connect(host, port);
		MemcachedClient client = MemcachedConnection.getConnection();
		
		//set in memcached
		String key = UUID.randomUUID().toString();
		String content = "slok_" + UUID.randomUUID().toString();
		int seconds = 1;
		client.set(key, seconds, content);
		
		//sleep (0,1 seconds)
		Thread.sleep(100);
		
		//get from memcached and check
		String result = (String) client.get(key);
		assertEquals(content, result);
	}

}
