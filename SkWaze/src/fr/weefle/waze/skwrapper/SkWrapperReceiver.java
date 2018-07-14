package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Client.ClientSocketJSONEvent;

public class SkWrapperReceiver implements Listener {
	
	private static String message;
	
	@EventHandler
    public void onSocketMessage(ClientSocketJSONEvent e) {
		String channel = e.getChannel(); // The channel name
	    
        if(channel.equals("SkWrapper-list")) {
            String message = e.getExtraString("message");
            SkWrapperReceiver.message = message;
        }
	}
	
	public static String getServers() {
		
		return message;
		
	}

}
