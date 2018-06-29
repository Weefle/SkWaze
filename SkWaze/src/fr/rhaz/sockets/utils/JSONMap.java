package fr.rhaz.sockets.utils;

import java.util.HashMap;
import java.util.Map;

public class JSONMap extends HashMap<String,Object> {
	
	private static final long serialVersionUID = 4878083193340506183L;

	public JSONMap() {
		super();
	}
	
	public JSONMap(Map<String,Object> map) {
		super(map);
	}
	
	public JSONMap(Object... entries) {
		
		String key = null;
		for(Object o:entries){
			
			if(key == null) {
				if(o instanceof String)
					key = (String) o;
			
			}else {
				if(!key.isEmpty())
					this.put(key, o);
				
				key = null;
			}
		}
	}
	
	public String getChannel(){
		return getExtraString("channel");
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getExtra(String key, Class<T> type) {
		return (T) get(key);
	}
	
	public String getExtraString(String key) {
		return getExtra(key, String.class);
	}
	
	public int getExtraInt(String key) {
		return getExtra(key, int.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getExtraMap(String key) {
		return getExtra(key, Map.class);
	}
	
}
