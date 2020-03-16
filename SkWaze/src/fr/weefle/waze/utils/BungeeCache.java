package fr.weefle.waze.utils;

import org.bukkit.Bukkit;

import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.PluginMessageRequest;

public class BungeeCache {
	
	public int onlineGlobal;
	public String serverList;
	
	public BungeeCache(Waze main) {
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				
				PluginMessageRequest pmr = new PluginMessageRequest("SkWrapper-online-count-global") {
					
					@Override
					public void onAnswer(PluginMessage response) {
						
						onlineGlobal = response.getDataAsInt("global-count");
						
					}
				};
				Waze.getComApi().sendRequest(pmr);
				
				PluginMessageRequest pmr1 = new PluginMessageRequest("SkWrapper-get-servers") {
					
					@Override
					public void onAnswer(PluginMessage response) {
						
						serverList = response.getData("server-list");
						
					}
				};
				Waze.getComApi().sendRequest(pmr1);
				
				
				
				/*PacketGetOnlineCountGlobal onlineCount = new PacketGetOnlineCountGlobal();
				Object obj = onlineCount.send();
				onlineGlobal = (int) obj;
				
				PacketGetServers serverGlobal = new PacketGetServers();
				Object obj1 = serverGlobal.send();
				serverList = (String) obj1;*/
				
				
			}
		}, 20L, 20L);
		
	}
	
	public int getOnlineCountGlobal() {
		return onlineGlobal;
	}
	
	public String getServerList() {
		return serverList;
	}

}
