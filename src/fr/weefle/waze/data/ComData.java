package fr.weefle.waze.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public abstract class ComData {
	
	private Map<String, String> data;
	
	public ComData() {
		data=new HashMap<>();
	}
	
	public void setData(String key, String value) {
		
		data.put(key, value);
		
	}
	
	public String getData(String key) {
		return data.get(key);
	}
	
	public int getDataAsInt(String key) {
		return Integer.parseInt(data.get(key));
	}
	
	public boolean getDataAsBoolean(String key) {
		return Boolean.parseBoolean(data.get(key));
	}
	
	public long getDataAsString(String key) {
		return Long.parseLong(data.get(key));
	}
	
	public double getDataAsDouble(String key) {
		return Double.parseDouble(data.get(key));
	}
	
	public float getDataAsFloat(String key) {
		return Float.parseFloat(data.get(key));
	}
	
	public short getDataAsShort(String key) {
		return Short.parseShort(data.get(key));
	}
	
	public byte getDataAsByte(String key) {
		return Byte.parseByte(data.get(key));
	}
	
	public boolean containsData(String key) {
		return data.containsKey(key);
	}
	
	public String encodeData() {
		return new Gson().toJson(data);
	}
	
	public void decodeData(String data) {
		this.data = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {
			
		}.getType());
	}

}
