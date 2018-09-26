package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.rhaz.sockets.socket4mc.Socket4Bukkit;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Client.ClientSocketHandshakeEvent;
import fr.rhaz.sockets.utils.JSONMap;

public class SkWrapperSender implements Listener {
	
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

}
