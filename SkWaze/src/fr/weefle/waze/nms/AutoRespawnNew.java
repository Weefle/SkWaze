package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

public class AutoRespawnNew implements AutoRespawnAPI {

	@Override
	public void respawn(Player p) {
		
		p.spigot().respawn();
		
	}
	
}
