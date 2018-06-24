package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Client.ClientSocketHandshakeEvent;
import fr.rhaz.sockets.utils.JSONMap;

public class SkWrapperListener implements Listener {
	
	public void sendPing() {
	    JSONMap map = new JSONMap(
	        "message", "Successfully connected to the server!"
	    );

	    Socket4Bukkit.getClient().write("SkWrapper-msg", map);
	}
		@EventHandler
		public void onHandshake(ClientSocketHandshakeEvent e){
		    sendPing();
		}
		
		/*@EventHandler
		public void onSocketMessage(ServerSocketJSONEvent e){

		    String channel = e.getChannel(); // The channel name
		 
		    if(!channel.equals("MyChannel")) return;

		    String name = e.getName(); // The name of the server

		    String message = e.getExtraString("message");
		 
		    getLogger().info("Received message from " + name + ": " + message);

		    }*/

}
