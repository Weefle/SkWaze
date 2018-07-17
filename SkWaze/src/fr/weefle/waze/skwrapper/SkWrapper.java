package fr.weefle.waze.skwrapper;

import fr.rhaz.sockets.socket4mc.Socket4Bukkit;
import fr.rhaz.sockets.utils.JSONMap;

public class SkWrapper {
	
	public static void createServer(String name, String template) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		JSONMap map = new JSONMap("message", sb);
	    Socket4Bukkit.getClient().write("SkWrapper-create", map);	
		
	}
	
	public static void startServer(String name, String template) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		JSONMap map = new JSONMap("message", sb);
	    Socket4Bukkit.getClient().write("SkWrapper-start", map);
		
	}

public static void stopServer(String name, String template) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		JSONMap map = new JSONMap("message", sb);
	    Socket4Bukkit.getClient().write("SkWrapper-stop", map);
		
	}

public static String getServers(String template) {
	
	JSONMap map = new JSONMap("message", template);
    Socket4Bukkit.getClient().write("SkWrapper-list", map);
    	return SkWrapperReceiver.getServers();
    
}
}