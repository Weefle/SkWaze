package fr.rhaz.sockets.server;

import fr.rhaz.sockets.utils.JSONMap;

public abstract class SocketServerApp {
	
	public void log(String err) {}

	public void onConnect(SocketMessenger mess) {}
	
	public void onDisconnect(SocketMessenger mess) {}

	public void onHandshake(SocketMessenger mess, String name) {}

	public abstract void onMessage(SocketMessenger mess, JSONMap map);

	public void run(Runnable runnable) {
		new Thread(runnable).start();
	}

}