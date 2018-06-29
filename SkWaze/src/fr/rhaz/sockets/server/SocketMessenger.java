package fr.rhaz.sockets.server;

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

public class SocketMessenger implements Runnable, SocketWriter {
	
	private AtomicBoolean handshaked = new AtomicBoolean(false);
	
	private Message mRSA = new Message();
	private Message mAES = new Message();
	
	private Map<String, Message> messages = new HashMap<String, Message>();
	
	private Data Data = new Data();
	private Security Security = new Security();
	private IO IO = new IO();

	public class Data {
		private Socket socket;
		private SocketServer server;
	}

	public class Security {
		private int level; // 0 = no security; 1 = AES encryption (b64 key sent); 2 = AES encryption, RSA
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

	public class IO {
		private BufferedReader reader;
		private PrintWriter writer;

		public void set(Socket socket) throws IOException {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		}
	}

	public SocketMessenger(SocketServer socketServer, final Socket socket, int security) {
		Data.socket = socket;
		Data.server = socketServer;
		Security.level = security; // Setting security level
		Security.reset(); // Reset security data
		
		if (Data.server.isEnabled() && isConnectedAndOpened()) {
			try {
				
				IO.set(socket);
				
				if (Security.level == 0) 
					write("SocketAPI", "handshake");
				
				if (Security.level == 1) {
					IO.writer.println("1");
					Data.server.Data.app.log("Self AES: " + AES.toString(Security.Self.AES));
					IO.writer.println(AES.toString(Security.Self.AES));
					IO.writer.println("--end--");
					IO.writer.flush();
				}
				if (Security.level == 2) {
					IO.writer.println("2");
					IO.writer.println(RSA.savePublicKey(Security.Self.RSA.getPublic()));
					IO.writer.println("--end--");
					IO.writer.flush();
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void run() {
		while (Data.server.isEnabled() && isConnectedAndOpened()) { // While connected
			
			try {
				loop:{
				
					// Read the line
					String read = IO.reader.readLine();
					Data.server.Data.app.log("read: " + read);
					
					// If end of stream, close
					if (read == null) 
						break; 
				
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
						
						// Now we send our AES key encrypted with target RSA
						String aes = AES.toString(Security.Self.AES);
						String rsa = RSA.encrypt(aes, Security.Target.RSA);
						
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
						
						Data.server.Data.app.log("Target AES: " + key);
						
						// Received AES key encrypted in RSA
						if(Security.level == 2) 
							key = RSA.decrypt(key, Security.Self.RSA.getPrivate());

						// We save it
						Security.Target.AES = AES.toKey(key);
						
						// Now we handshake
						write("SocketAPI", "handshake");
						break loop; // Wait until we receive another message
						
					}
					
					// Is the line encrypted in AES?
					if (Security.level >= 1)
						read = AES.decrypt(read, Security.Self.AES);
					
					Data.server.Data.app.log("<- " + read);
					
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
					JSONMap map = message.emr();
					
					if(!map.get("password").equals(Data.server.Data.password))
						break loop;
					
					handshake:{
						
						// Is it our channel?
						if (!map.get("channel").equals("SocketAPI"))
							break handshake;
							
						// Is the message a handshake?
						if (!map.get("data").equals("handshake"))
							break handshake;
								
						handshaked.set(true);
						
						if(!(map.get("name") instanceof String))
							break handshake;
						
						Data.server.getApp().onHandshake(this, map.getExtraString("name"));
						write("SocketAPI", "handshaked");
						break loop; // Wait until we receive another message
					}
					
					// Send the object to the app
					Data.server.getApp().onMessage(this, new JSONMap(map));
					
				}
			
				Thread.sleep(100); // Wait 100 milliseconds before reading another line
			} catch (IOException | GeneralSecurityException | InterruptedException e) {
				break;
			}
		}
		close();
	}

	public SocketServer getServer() {
		return Data.server;
	}

	public boolean isConnectedAndOpened() {
		return getSocket().isConnected() && !getSocket().isClosed();
	}

	public boolean isHandshaked() {
		return handshaked.get();
	}

	public void write(String channel, String data) {
		write(channel, new JSONMap("data", data));
	}
	
	public void write(String channel, JSONMap data) {
		try {
			
			data.put("name", Data.server.Data.name);
			data.put("channel", channel);
			
			String json = Sockets.gson().toJson(data);
			write(json);
		}catch(NullPointerException ex) {}
	}

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
				Data.socket.close();
				Data.server.Data.messengers.remove(this);
				Data.server.getApp().onDisconnect(this);
			} catch (IOException e) {
				return e;
			}
		}
		return null;
	}
	
	public boolean isEnabled() {
		return Data.server.isEnabled();
	}

	public Socket getSocket() {
		return Data.socket;
	}
}
