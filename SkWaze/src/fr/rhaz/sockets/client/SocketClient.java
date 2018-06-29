package fr.rhaz.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.SecretKey;

import fr.rhaz.sockets.SocketWriter;
import fr.rhaz.sockets.Sockets;
import fr.rhaz.sockets.utils.AES;
import fr.rhaz.sockets.utils.JSONMap;
import fr.rhaz.sockets.utils.Message;
import fr.rhaz.sockets.utils.RSA;

public class SocketClient implements Runnable, SocketWriter {
	
	private AtomicBoolean enabled = new AtomicBoolean(false);
	private AtomicBoolean handshaked = new AtomicBoolean(false);
	
	private Message mRSA = new Message();
	private Message mAES = new Message();
	
	private Map<String, Message> messages = new HashMap<>();
	
	private Data Data = new Data();
	private Security Security = new Security();
	private IO IO = new IO();

	public class Data {
		private String host;
		private int port;
		private Socket socket;
		private SocketClientApp app;
		public String password;
		public String name;

		public void set(String name, String host, int port, Socket socket, SocketClientApp app, String password) {
			Data.name = name;
			Data.host = host;
			Data.port = port;
			Data.socket = socket;
			Data.app = app;
			Data.password = password;
		}
	}

	public class IO {
		private BufferedReader reader;
		private PrintWriter writer;

		public void set(Socket socket) throws IOException {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		}
	}

	public class Security {
		private int level = 0; // 0 = no security; 1 = AES encryption (b64 key sent); 2 = AES encryption, RSA
							// handshake (RSA used for sending AES key)
		private Target Target = new Target();
		private Self Self = new Self();

		private class Target {
			private PublicKey RSA;
			private SecretKey AES;
		}

		private class Self {
			private KeyPair RSA;
			private SecretKey AES;
		}

		public void reset() {
			Target.AES = null;
			Target.RSA = null;
			Self.AES = AES.generateKey();
			Self.RSA = RSA.generateKeys();
		}
	}

	public SocketClient(SocketClientApp app, String name, String host, int port, String password) {
		Data.set(name, host, port, new Socket(), app, password);
		enabled.set(true);
	}
	
	public void start() {
		Data.app.run(this);
	}

	public void run() {
		while (enabled.get()) {
			try {
				Data.socket = new Socket(Data.host, Data.port); // Connection
				Data.socket.setTcpNoDelay(true); // Socket option
				Data.socket.setPerformancePreferences(0, 1, 2);

				Data.app.onConnect(this); // Trigger onConnect event
				Security.reset(); // Reset security data
				IO.set(Data.socket); // Open socket streams
				handshaked.set(false); // Default not handshaked

				interact();
			} catch (IOException e) {
				close();
			}
		}
	}
	
	public void interact() {
		while (enabled.get() && isConnectedAndOpened()) { // While connected
			
			try {
				loop:{
				
					// Read the line
					String read = IO.reader.readLine();
					Data.app.log("read: " + read);
					// If end of stream, close
					if (read == null) 
						throw new IOException(); 
					
					if(read.equals("1")) {
						Security.level = 1;
						break loop;
					}
					
					if(read.equals("2")) {
						Security.level = 2;
						break loop;
					}
				
					rsa:{
						
						// Is RSA enabled? Do we received the RSA key?
						if (!(Security.level >= 2 && Security.Target.RSA == null)) 
							break rsa; 
						
						// Is the message fully received?
						if (!read.equals("--end--")) { 
							mRSA.add(read); // Add this line
							break loop; // Read another line
						}
						
						String key = mRSA.egr();
						// Yay, we received the RSA key
						// Convert it to a PublicKey object
						Security.Target.RSA = RSA.loadPublicKey(key); 
						
						// Now we send our RSA key
						String rsa = RSA.savePublicKey(Security.Self.RSA.getPublic());
						
						IO.writer.println(rsa);
						IO.writer.println("--end--");
						IO.writer.flush();
						break loop; // Wait until we receive another message
					}
					
					aes:{
						
						// Is AES enabled? Do we already received AES?
						if (!(Security.level >= 1 && Security.Target.AES == null))
							break aes;
	
						// Is it the end of the key?
						if (!read.equals("--end--")) {
							mAES.add(read); // Add this line
							break loop; // Read another line
						}
						
						String key = mAES.egr();
						
						Data.app.log("Target AES: " + key);
						
						// Received AES key encrypted in RSA
						if(Security.level == 2) 
							key = RSA.decrypt(key, Security.Self.RSA.getPrivate());

						// We save it
						Security.Target.AES = AES.toKey(key);
						
						// Now we send our AES key encrypted with server RSA
						String aes = AES.toString(Security.Self.AES);
						
						if(Security.level == 2)
							aes = RSA.encrypt(aes, Security.Target.RSA);
						
						IO.writer.println(aes);
						IO.writer.println("--end--");
						IO.writer.flush();
						break loop; // Wait until we receive another message
						
					}
					
					// Is the line encrypted in AES?
					if (Security.level >= 1)
						read = AES.decrypt(read, Security.Self.AES);
					
					Data.app.log("<- " + read);
					
					// If line is null or empty, read another
					if (read == null || read.isEmpty())
						break loop;
					
					String[] split = read.split("#");
					if(split.length < 2)
						break loop;
					
					String id = split[0];
					
					String data = read.substring(id.length() + 1);

					Message message;
					if(messages.containsKey(id))
						message = messages.get(id);
					else {
						message = new Message();
						messages.put(id, message);
					}

					// Is message fully received?
					if (!data.equals("--end--")) {
						message.add(data); // Add line
						break loop; // Read another line
					}
					
					messages.remove(id);
					
					// Convert message to an object
					Map<String, Object> map = message.emr();
					
					handshake:{
						
						// Is it our channel?
						if (!map.get("channel").equals("SocketAPI"))
							break handshake;
							
						// Is the message a handshake?
						if (map.get("data").equals("handshake")) {
							write("SocketAPI", "handshake");
							break loop;
						}
						
						if (!map.get("data").equals("handshaked"))
							break handshake;
								
						handshaked.set(true);
						Data.app.onHandshake(this);
						break loop; // Wait until we receive another message
					}
					
					// Send the object to the app
					Data.app.onMessage(this, new JSONMap(map));
					
				}
			
				Thread.sleep(100); // Wait 100 milliseconds before reading another line
			} catch (IOException | GeneralSecurityException | InterruptedException e) {
				break;
			}
		}
		close();
	}

	public int getPort() {
		return Data.port;
	}

	public String getHost() {
		return Data.host;
	}

	public Socket getSocket() {
		return Data.socket;
	}
	
	public int getSecurityLevel() {
		return Security.level;
	}
	
	public String getName() {
		return Data.name;
	}

	public boolean isConnectedAndOpened() {
		return Data.socket.isConnected() && !Data.socket.isClosed();
	}

	public boolean isHandshaked() {
		return handshaked.get();
	}

	public void write(String channel, String data) {
		write(channel, new JSONMap("data", data));
	}
	
	public void write(String channel, JSONMap data) {
		try {
			
			data.put("name", Data.name);
			data.put("channel", channel);
			data.put("password", Data.password);
			
			String json = Sockets.gson().toJson(data);
			write(json);
		}catch(NullPointerException ex) {}
	}

	@Deprecated
	public synchronized void write(String data) {
		try {
			
			int id = new Random().nextInt(1000);
			
			String[] split = Sockets.split(data, 20);
				
			for (String str : split) {
				
				str = id + "#" + str;
				
				if(Security.level >= 1)
					str = AES.encrypt(str, Security.Target.AES);
				
				IO.writer.println(str);
				
			}
			
			String end = id + "#" + "--end--";
			
			if(Security.level >= 1)
				end = AES.encrypt(end, Security.Target.AES);
			
			IO.writer.println(end);
			IO.writer.flush();
			
			Thread.sleep(100);
			
		} catch (NullPointerException | InterruptedException e) {}
	}

	public IOException close() {
		if (!Data.socket.isClosed()) {
			try {
				handshaked.set(false);
				Data.socket.close();
				Data.app.onDisconnect(this);
			} catch (IOException e) {
				return e;
			}
		}
		return null;
	}

	public IOException interrupt() {
		enabled.set(false);
		return close();
	}

	public boolean isEnabled() {
		return enabled.get();
	}
}