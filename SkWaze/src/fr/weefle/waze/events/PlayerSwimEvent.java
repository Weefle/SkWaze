package fr.weefle.waze.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
    		if(e.getTo().getBlock().isLiquid() && location.getBlock().getType() != Material.AIR && e.getTo().getBlock().getType() == Material.STATIONARY_WATER || e.getTo().getBlock().getType() == Material.WATER) {
    			
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
