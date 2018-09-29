package fr.weefle.waze.utils;

import org.bukkit.scheduler.BukkitRunnable;
import fr.weefle.waze.Waze;

public class HologramTracker {
	
	public void holoTrack() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(NMS.getInstance().getHolograms().holos != null) {
						if(NMS.getInstance().getHolograms().players != null) {
						for(int p=0;p<NMS.getInstance().getHolograms().players.size();p++) {
						//	NMS.getInstance().getHolograms().players.get(p).sendMessage("oui");
						//	NMS.getInstance().getHolograms().players.get(p).sendMessage(NMS.getInstance().getHolograms().holosf.toString());
							if(NMS.getInstance().getHolograms().holosf != null) {
								for(String h : NMS.getInstance().getHolograms().holosf) {
									//NMS.getInstance().getHolograms().players.get(p).sendMessage("yeah");
									//NMS.getInstance().getHolograms().players.get(p).sendMessage(NMS.getInstance().getHolograms().holofollow.get(h).toString());
									NMS.getInstance().getHolograms().holofollow.get(h).teleport(NMS.getInstance().getHolograms().players.get(p).getLocation());
								}}}}
			}
			}
			
		}.runTaskTimer(Waze.getInstance(), 1L, 1L);
		
	}

}

