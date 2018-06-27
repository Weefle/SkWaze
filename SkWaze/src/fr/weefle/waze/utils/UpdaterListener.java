package fr.weefle.waze.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdaterListener implements Listener {
	
	/*private Main m;
	public HeadListener(Main m) {
		this.m = m;
	}*/
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		if(Updater.enabledingame) {
			if(e.getPlayer().isOp()) {
			if(Updater.update) {
			e.getPlayer().sendMessage("§6[§2Updater§6] §3There is an update for §5SkWaze§3, look at the link in the console!");
			}else{
				//e.getPlayer().sendMessage("§6[§2Updater�6] §3There isn't update for SkWaze!");
				return;
			}
		}
		}
		
	}

}