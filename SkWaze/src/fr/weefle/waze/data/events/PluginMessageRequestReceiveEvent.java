package fr.weefle.waze.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;

public class PluginMessageRequestReceiveEvent extends Event {
	
	
	public static final HandlerList handlers = new HandlerList();
	private Waze instance;
	private PluginMessage pm;
	private PluginMessage response;
	
	public PluginMessageRequestReceiveEvent(Waze instance, PluginMessage pm) {
		this.instance = instance;
		this.pm = pm;
	}

	@Override
	public HandlerList getHandlers() {
		
		return handlers;
		
	}
	
	public Waze getInstance() {
		return instance;
	}
	
	public PluginMessage getMessage() {
		return pm;
	}
	
	public PluginMessage getResponse() {
		return response;
	}
	
	public void setResponse(PluginMessage response) {
	
		this.response = response;
		
	}

}
