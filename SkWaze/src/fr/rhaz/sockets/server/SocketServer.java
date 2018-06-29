package fr.rhaz.sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer implements Runnable {
	public Data Data = new Data();

	public class Data {
		public int port;
		public SocketServerApp app;
		public ServerSocket server;
		public int security;
		public ArrayList<SocketMessenger> messengers;
		public String name;
		public String password;

		public void set(String name, int port, SocketServerApp app, int security, String password) throws IOException {
			Data.name = name;
			Data.port = port;
			Data.app = app;
			Data.security = security;
			Data.server = new ServerSocket();
			Data.messengers = new ArrayList<>();
			Data.password = password;
		}
	}

	public SocketServer(SocketServerApp app, String name, int port, int security, String password) {
		try {
			Data.set(name, port, app, security, password);
		} catch (IOException e) {}
	}

	public IOException start() {
		try {
			Data.server = new ServerSocket(Data.port);
			Data.app.run(this);
			return null;
		} catch (IOException e) {
			return e;
		}
	}

	public int getPort() {
		return Data.port;
	}

	public SocketServerApp getApp() {
		return Data.app;
	}

	public ServerSocket getServerSocket() {
		return Data.server;
	}
	
	public int getSecurityLevel() {
		return Data.security;
	}
	
	public String getName() {
		return Data.name;
	}
	
	public ArrayList<SocketMessenger> getMessengers(){
		return Data.messengers;
	}

	@Override
	public void run() {
		while (!Data.server.isClosed()) {
			try {
				Socket socket = Data.server.accept(); // Accept new connection
				socket.setTcpNoDelay(true); // Set socket option
				socket.setPerformancePreferences(0, 1, 2);

				SocketMessenger messenger = new SocketMessenger(this, socket, Data.security); // Create a new messenger for this socket
				Data.messengers.add(messenger); // Add this messenger to the list
				Data.app.onConnect(messenger); // Trigger onConnect event
				Data.app.run(messenger); // Run the messenger
			} catch (IOException e) {}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public IOException close() {
		if (!Data.server.isClosed()) {
			try {
				Data.server.close(); // Close the server
				for (SocketMessenger messenger : new ArrayList<>(Data.messengers))
					messenger.close(); // Close the messengers
			} catch (IOException e) {
				return e;
			}
		}
		return null;
	}

	public boolean isEnabled() {
		return !Data.server.isClosed();
	}
}