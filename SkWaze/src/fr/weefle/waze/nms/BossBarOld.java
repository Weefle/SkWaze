package fr.weefle.waze.nms;

import org.bukkit.entity.Player;
import me.confuser.barapi.BarAPI;

public class BossBarOld implements BossBarAPI {

	@SuppressWarnings("deprecation")
	@Override
	public void sendBossBar(Player p, String message, double percent, String color, String id) {
		
			BarAPI.setMessage(p, message, (float) percent * 100);
			
	}

	@SuppressWarnings("deprecation")
	@Override
	public void removeBossBar(Player p, String id) {
		
			BarAPI.removeBar(p);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendBossBarTimer(Player p, String message, double percent, String color, int time, String id) {
		
            BarAPI.setMessage(p, message, time / 20);
          
        }
	
}