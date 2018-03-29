package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

public class AutoRespawnNew implements AutoRespawn {

	@Override
	public void respawn(Player p) {
		
		p.spigot().respawn();
		
	}
	
}
