package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

public interface BossBarAPI {
	
	public void sendBossBar(Player p, String message, double percent, String color, String id);
	
	public void removeBossBar(Player p, String id);
	
	public void sendBossBarTimer(Player p, String message, double percent, String color, int time, String id);

}
