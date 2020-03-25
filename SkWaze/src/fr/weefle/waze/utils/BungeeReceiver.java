package fr.weefle.waze.utils;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ch.njol.skript.variables.Variables;
import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.events.PluginMessageReceiveEvent;

public class BungeeReceiver implements Listener {
	
	public static HashMap<Player, String> servers = new HashMap<>();
	
	@EventHandler
	public void onReceive(PluginMessageReceiveEvent e) throws IOException {
		PluginMessage pm = e.getMessage();
		if(pm.getType().equalsIgnoreCase("SkWrapper-update-variables")) { 
        	String ID = pm.getData("ID");
        	Object value = pm.getData("value");
        	//Bukkit.getLogger().warning(ID + " : " + value);
        	Variables.setVariable(ID, value, (Event)null, false);
	         }
		else if(pm.getType().equalsIgnoreCase("SkWrapper-player-server-connect")) { 
	String player = pm.getData("player");
	String server = pm.getData("server");
	
	Player p = Bukkit.getPlayer(player);
	
	servers.put(p, server);
	
	
     }
		else if(pm.getType().equalsIgnoreCase("SkWrapper-player-server-disconnect")) { 
			String player = pm.getData("player");
			
			Player p = Bukkit.getPlayer(player);
			
			servers.remove(p);
			
			
		     }
	        else {
	        	return;
	        }
	        }
	
	}