package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Server.ServerSocketJSONEvent;

public class SkWrapperReceiver implements Listener {
	
	private String message;
	
	@EventHandler
    public void onSocketMessage(ServerSocketJSONEvent e) {
		String channel = e.getChannel(); // The channel name
	    
        if(channel.equals("SkWrapper-list")) {
            String message = e.getExtraString("message");
        	this.message = message;
        }
	}
	
	public String getServers() {
		
		return this.message;
		
	}

}
