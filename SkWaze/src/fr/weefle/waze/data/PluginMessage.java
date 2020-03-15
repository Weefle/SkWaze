package fr.weefle.waze.data;

import java.util.UUID;

public class PluginMessage extends ComData {
	
	public PluginMessage(String type) {
		
		super();
		setData("type", type);
		setData("uuid", UUID.randomUUID().toString());
		setData("request_response", Boolean.FALSE.toString());
		
	}
	
	public PluginMessage(String type, String uuid) {
		
		super();
		setData("type", type);
		setData("uuid", uuid);
		setData("request_response", Boolean.FALSE.toString());
		
	}
	
	public PluginMessage() {
		super();
		
	}
	
	public static PluginMessage getFromJson(String json) {
		PluginMessage pm = new PluginMessage();
		pm.decodeData(json);
		return pm;
	}
	
	public String getType() {
		return getData("type");
	}
	
	public String getUUID() {
		return getData("uuid");
	}
	
	public boolean requireResponse() {
		return getDataAsBoolean("require_response");
	}
	
}
