package fr.weefle.waze.skwrapper;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class BungeeReceiver implements Listener {
	
	public static HashMap<Player, String> servers = new HashMap<>();
	public static ArrayList<String> serverson = new ArrayList<>();
	
	/*@EventHandler
	public void onReceive(PluginMessageReceiveEvent e) {
		PluginMessage pm = e.getMessage();
		if(pm.getType().equalsIgnoreCase("SkWrapper-update-variables")) { 
        	String ID = pm.getData("ID");
        	Object value = Waze.getInstance().getSerializableManager().deserialize(pm.getData("value"));
        	//Bukkit.getLogger().warning(ID + " : " + value);
        	Variables.setVariable(ID, value, (Event)null, false);
	         }

	        }*/
	
	}