package org.mdissjava.thumbnailer.gearman.client;

import org.gearman.client.GearmanClient;
import org.gearman.client.GearmanClientImpl;
import org.gearman.common.GearmanJobServerConnection;
import org.gearman.common.GearmanNIOJobServerConnection;

public abstract class AbstractGearmanClient {
	
	private GearmanClient client = null;
	
	public AbstractGearmanClient() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractGearmanClient(String host, int port) {
		this(new GearmanNIOJobServerConnection(host, port));
	}
	
	public AbstractGearmanClient(GearmanJobServerConnection connection) {
		client = new GearmanClientImpl();
		client.addJobServer(connection);
	}

	public void disconnect() {
		this.client.shutdown();
		
	}
}
