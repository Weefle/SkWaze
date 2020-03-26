package fr.weefle.waze.utils;

import java.io.IOException;
import java.util.ArrayList;
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
	public static Integer onlineCount = 0;
	public static String serverList = "";
	public static ArrayList<String> serverson = new ArrayList<>();
	
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
		/*else if(pm.getType().equalsIgnoreCase("SkWrapper-player-server-disconnect")) { 
			String player = pm.getData("player");
			
			Player p = Bukkit.getPlayer(player);
			
			servers.remove(p);
			
			
		     }*/
		
		else if(pm.getType().equalsIgnoreCase("SkWrapper-online-count-global")) { 
			 onlineCount = pm.getDataAsInt("global-count");
			
		     }
		else if(pm.getType().equalsIgnoreCase("SkWrapper-get-servers")) { 
			 serverList = pm.getData("server-list");
			
		     }
		else if(pm.getType().equalsIgnoreCase("SkWrapper-add-started")) { 
			String server = pm.getData("server");
			 
			serverson.add(server);
			
		     }
		else if(pm.getType().equalsIgnoreCase("SkWrapper-remove-started")) { 
			String server = pm.getData("server");
			 
			serverson.remove(server);
			
		     }
	        else {
	        	return;
	        }
	        }
	
	}