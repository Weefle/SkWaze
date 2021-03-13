package fr.weefle.waze.skwrapper.events;

import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.events.PluginMessageReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PlayerJoinServerEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private static final PlayerJoinServerEventListener listener = new PlayerJoinServerEventListener();

    private final HashMap<Player, String> servers;

    private final Player player;

    private boolean cancel = false;

    public PlayerJoinServerEvent(Player player, HashMap<Player, String> servers) {
        this.player = player;
        this.servers = servers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getServer() {
        return this.servers.entrySet().iterator().next().getValue();
    }

    private static class PlayerJoinServerEventListener implements Listener {
        private final HashMap<Player, String> servers = new HashMap<>();

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerJoinServer(PluginMessageReceiveEvent e) {
            PluginMessage pm = e.getMessage();
            if (pm.getType().equalsIgnoreCase("SkWrapper-player-server-connect")) {
                String player = pm.getData("player");
                String server = pm.getData("server");
                Player p = Bukkit.getPlayer(player);
                //Bukkit.getLogger().warning("Connect Event: " + p + "->" + server);
                this.servers.put(p, server);
                Bukkit.getPluginManager().callEvent(new PlayerJoinServerEvent(p, this.servers));
            }
        }

        private PlayerJoinServerEventListener() {}
    }

    public static void register(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(listener, (Plugin)plugin);
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean b) {
        this.cancel = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
