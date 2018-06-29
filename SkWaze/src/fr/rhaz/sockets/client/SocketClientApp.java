package fr.rhaz.sockets.client;

import fr.rhaz.sockets.utils.JSONMap;

public abstract class SocketClientApp {
	
	public void log(String err) {}

	public void onConnect(SocketClient client) {}

	public void onDisconnect(SocketClient client) {}

	public void onHandshake(SocketClient client) {}

	public abstract void onMessage(SocketClient client, JSONMap map);
	
	public void run(Runnable runnable) {
		new Thread(runnable).start();
	}
}