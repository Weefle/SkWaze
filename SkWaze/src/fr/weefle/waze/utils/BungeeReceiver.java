package fr.weefle.waze.utils;

import java.io.IOException;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ch.njol.skript.variables.Variables;
import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.events.PluginMessageReceiveEvent;

public class BungeeReceiver implements Listener {
	
	@EventHandler
	public void onReceive(PluginMessageReceiveEvent e) throws IOException {
		PluginMessage pm = e.getMessage();
if(pm.getType().equalsIgnoreCase("SkWrapper-update-variables")) { 
        	String ID = pm.getData("ID");
        	String value = pm.getData("value");
        	Object obj = (Object) value;
        	Variables.setVariable(ID, value, (Event)null, false); 	
	         }
	        else {
	        	return;
	        }
	        }
	
	}