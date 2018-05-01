package fr.weefle.waze.nms;

import org.bukkit.entity.Player;
import me.confuser.barapi.BarAPI;

public class BossBarOld implements BossBarAPI {

	@Override
	public void sendBossBar(Player p, String message, double percent, String color, String id) {
		
			BarAPI.setMessage(p, message, (float) percent * 100);
			
	}

	@Override
	public void removeBossBar(Player p, String id) {
		
			BarAPI.removeBar(p);
		
	}

	@Override
	public void sendBossBarTimer(Player p, String message, double percent, String color, int time, String id) {
		
            BarAPI.setMessage(p, message, time / 20);
          
        }
	
}