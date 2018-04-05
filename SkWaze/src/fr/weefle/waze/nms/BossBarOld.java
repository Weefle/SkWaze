package fr.weefle.waze.nms;

import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI.Color;
import org.inventivetalent.bossbar.BossBarTimer;
import org.inventivetalent.bossbar.PacketBossBar;

public class BossBarOld implements BossBarAPI {
	
	private PacketBossBar pb = null;
	@Override
	public void sendBossBar(Player p, String message, double percent, String color, String id) {
		
			//BarAPI.setMessage(p, message, (float) percent * 100);
		pb.setMessage(message);
        pb.setProgress((float) percent * 100);
        pb.setColor(Color.valueOf(color));
        pb.addPlayer(p);
			
	}

	@Override
	public void removeBossBar(Player p, String id) {
		
			//BarAPI.removeBar(p);
			pb.removePlayer(p);
		
	}

	@Override
	public void sendBossBarTimer(Player p, String message, double percent, String color, int time, String id) {
		
            //BarAPI.setMessage(p, message, time / 20);
            new BossBarTimer(pb, (float) percent * 100, time / 20);
            pb.setMessage(message);
            pb.setColor(Color.valueOf(color));
            pb.addPlayer(p);
          
        }
	
}