package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Server.ServerSocketJSONEvent;

public class SkWrapperReceiver implements Listener {
	
	@EventHandler
    public String onSocketMessage(ServerSocketJSONEvent e) {
		String channel = e.getChannel(); // The channel name
	    
        if(channel.equals("SkWrapper-list")) {
            String message = e.getExtraString("message");
        	return message;
        }
		return null;
	}

}
