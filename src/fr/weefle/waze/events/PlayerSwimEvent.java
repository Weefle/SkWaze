package fr.weefle.waze.events;

import fr.weefle.waze.utils.NMS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class PlayerSwimEvent extends Event implements Cancellable{
	
	private static final HandlerList handlers = new HandlerList();
    private static final PlayerSwimEventListener listener = new PlayerSwimEventListener();
    private Player player;
    private boolean cancel = false;
    
    private static class PlayerSwimEventListener implements Listener {

    	@EventHandler
    	public void onSwim(PlayerMoveEvent e) {
    		Location location = e.getPlayer().getLocation();
    		location.setY(location.getBlockY() + 1);
    		Material mat = null;
			if(NMS.version.contains("v1_7") || NMS.version.contains("v1_8") || NMS.version.contains("v1_9") || NMS.version.contains("v1_10") || NMS.version.contains("v1_11")){
				mat = Material.valueOf("STATIONARY_WATER");
			}else{
				mat = Material.LEGACY_STATIONARY_WATER;
			}
    		if(Objects.requireNonNull(e.getTo()).getBlock().isLiquid() && location.getBlock().getType() != Material.AIR && e.getTo().getBlock().getType() == mat || e.getTo().getBlock().getType() == Material.WATER) {
    			
    			Bukkit.getPluginManager().callEvent(new PlayerSwimEvent(e.getPlayer()));
    			
    		}
    	}
    	
    }
    
    public PlayerSwimEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancel = arg0;
		
	}
	
	public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

	public HandlerList getHandlers(){

        return handlers;

    }

    public static HandlerList getHandlerList(){

        return handlers;

    }

}
