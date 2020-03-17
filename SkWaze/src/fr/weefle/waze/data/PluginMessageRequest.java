package fr.weefle.waze.data;

import java.util.UUID;

import org.bukkit.Bukkit;

public abstract class PluginMessageRequest extends ComData {
	
	public PluginMessageRequest(String type) {
		super();
		setData("type", type);
		setData("uuid", UUID.randomUUID().toString());
		setData("require_response", Boolean.TRUE.toString());
		try {
			setData("sender", Bukkit.getServerName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PluginMessageRequest() {
		super();
		
	}
	
	public abstract void onAnswer(PluginMessage response);
		
	public String getType() {
		return getData("type");
	}
	
	
	public String getUUID() {
		return getData("uuid");
	}
	
	public boolean requireResponse() {
		return getDataAsBoolean("require_response");
	}
	
	public String getSender() {
		return getData("sender");
	}

}
