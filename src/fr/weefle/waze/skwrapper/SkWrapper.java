package fr.weefle.waze.skwrapper;

import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class SkWrapper {
	
	public static void createServer(String name, String template) {

		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		String str = sb.toString();
		PacketCustom packet = new PacketCustom("SkWrapper-create", (Object) str);
		packet.send();
	}
	
	public static void startServer(String name, String template) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		String str = sb.toString();
		PacketCustom packet = new PacketCustom("SkWrapper-start", (Object) str);
		packet.send();
		
	}

	public static void stopServer(String name, String template) {

		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		String str = sb.toString();
		PacketCustom packet = new PacketCustom("SkWrapper-stop", (Object) str);
		packet.send();
		
	}

	public static void deleteServer(String name, String template) {

		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" ");
		sb.append(template);
		String str = sb.toString();
		PacketCustom packet = new PacketCustom("SkWrapper-delete", (Object) str);
		packet.send();
		
	}

	/*public static String getServers(String template) {
    	return SkWrapperReceiver.getServers();
    
	}*/
}