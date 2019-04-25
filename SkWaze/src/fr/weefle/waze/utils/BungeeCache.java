package fr.weefle.waze.utils;

import org.bukkit.Bukkit;

import fr.weefle.waze.Waze;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;
import me.dommi2212.BungeeBridge.packets.PacketGetServers;

public class BungeeCache {
	
	public int onlineGlobal;
	public String serverList;
	
	public BungeeCache(Waze main) {
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				
				PacketGetOnlineCountGlobal onlineCount = new PacketGetOnlineCountGlobal();
				Object obj = onlineCount.send();
				onlineGlobal = (int) obj;
				
				PacketGetServers serverGlobal = new PacketGetServers();
				Object obj1 = serverGlobal.send();
				serverList = (String) obj1;
				
				
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
