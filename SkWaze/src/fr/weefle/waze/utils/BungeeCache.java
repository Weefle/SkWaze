package fr.weefle.waze.utils;

import org.bukkit.Bukkit;

import fr.weefle.waze.Waze;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;

public class BungeeCache {
	
	public int onlineGlobal;
	
	public BungeeCache(Waze main) {
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				
				PacketGetOnlineCountGlobal onlineCount = new PacketGetOnlineCountGlobal();
				Object obj = onlineCount.send();
				onlineGlobal = (int) obj;
				
				
			}
		}, 10L, 10L);
		
	}

}
