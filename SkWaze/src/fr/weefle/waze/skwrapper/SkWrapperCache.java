package fr.weefle.waze.skwrapper;

import org.bukkit.Bukkit;

import fr.weefle.waze.Waze;

public class SkWrapperCache {
	
	private static Waze main;
	private static String message;
	public SkWrapperCache(Waze main) {
		SkWrapperCache.main = main;
	}
	
	public static void Cache() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			
			@Override
			public void run() {
				message = SkWrapperReceiver.getServers();
			}
		}, 0L, 20L);
	}
	
public static String getServers() {
		
		return message;
		
	}

}
