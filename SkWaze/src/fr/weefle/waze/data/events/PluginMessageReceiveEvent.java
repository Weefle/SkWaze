package fr.weefle.waze.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;

public class PluginMessageReceiveEvent extends Event {
	
	public static final HandlerList handlers = new HandlerList();
	private Waze instance;
	private PluginMessage pm;
	
	public PluginMessageReceiveEvent(Waze instance, PluginMessage pm) {
		this.instance = instance;
		this.pm = pm;
	}


	public HandlerList getHandlers() {
		
		return handlers;
		
	}
	
	 public static HandlerList getHandlerList(){

	        return handlers;

	    }
	
	public Waze getInstance() {
		return instance;
	}
	
	public PluginMessage getMessage() {
		return pm;
	}

}
