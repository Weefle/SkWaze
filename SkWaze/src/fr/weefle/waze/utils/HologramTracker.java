package fr.weefle.waze.utils;

import org.bukkit.scheduler.BukkitRunnable;

import fr.weefle.waze.Waze;

public class HologramTracker {
	
	public void holoTrack() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(int i=0;i<NMS.getInstance().getHolograms().players.size();i++) {
					for(int j=0;j<NMS.getInstance().getHolograms().holofollow.size();j++) {
				NMS.getInstance().getHolograms().holofollow.get(j).teleport(NMS.getInstance().getHolograms().players.get(i).getLocation());
				}}
				
			}
		}.runTaskTimer(Waze.getInstance(), 1L, 1L);
		
	}

}
