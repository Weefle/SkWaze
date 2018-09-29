package fr.weefle.waze.utils;

import java.util.Arrays;
import org.bukkit.scheduler.BukkitRunnable;
import fr.weefle.waze.Waze;

public class HologramTracker {
	
	public void holoTrack() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(NMS.getInstance().getHolograms().holos != null) {
						if(NMS.getInstance().getHolograms().players != null) {
						for(int p : Arrays.asList(NMS.getInstance().getHolograms().players.size())) {
							if(NMS.getInstance().getHolograms().holofollow != null) {					
						NMS.getInstance().getHolograms().holofollow.values().iterator().next().teleport(NMS.getInstance().getHolograms().players.get(p).getLocation());
						}}}
			}
			}
			
		}.runTaskTimer(Waze.getInstance(), 1L, 1L);
		
	}

}

