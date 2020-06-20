package fr.weefle.waze.skwrapper;

import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;

	public class SkWrapper {
	
	public static void createServer(String name, String template) {
		
	PluginMessage pm = new PluginMessage("SkWrapper-create");
	pm.setData("server", name);
	pm.setData("template", template);
	Waze.getComApi().sendMessage(pm);
	/*PacketCustom packet = new PacketCustom("SkWrapper-create", (Object) str);
	packet.send();*/
	}
	
	public static void startServer(String name, String template) {
		PluginMessage pm = new PluginMessage("SkWrapper-start");
		pm.setData("server", name);
		pm.setData("template", template);
		Waze.getComApi().sendMessage(pm);
		/*PacketCustom packet = new PacketCustom("SkWrapper-start", (Object) str);
		packet.send();*/
		
	}

	public static void stopServer(String name, String template) {
		
	PluginMessage pm = new PluginMessage("SkWrapper-stop");
	pm.setData("server", name);
	pm.setData("template", template);
	Waze.getComApi().sendMessage(pm);
	/*PacketCustom packet = new PacketCustom("SkWrapper-stop", (Object) str);
	packet.send();*/
		
	}

	public static void deleteServer(String name, String template) {
	
	PluginMessage pm = new PluginMessage("SkWrapper-delete");
	pm.setData("server", name);
	pm.setData("template", template);
	Waze.getComApi().sendMessage(pm);
	/*PacketCustom packet = new PacketCustom("SkWrapper-delete", (Object) str);
	packet.send();*/
		
	}

	/*public static String getServers(String template) {
    	return SkWrapperReceiver.getServers();
    
	}*/
}