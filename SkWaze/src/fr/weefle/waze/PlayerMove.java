package fr.weefle.waze;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import fr.weefle.events.EvtJump;

public class PlayerMove
  implements Listener
{
  @SuppressWarnings("deprecation")
@EventHandler
  public void onPlayerMove(PlayerMoveEvent event)
  {
    if (event.getTo().getY() > event.getFrom().getY())
    {
      Block block = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY() + 2.0D, event.getTo().getZ()));
      Block control = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY() - 2.0D, event.getTo().getZ()));
      if ((block.getTypeId() == 0) && (control.getTypeId() != 0))
      {
        EvtJump custom = new EvtJump(event.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(custom);
        if (custom.isCancelled()) {
          event.setCancelled(true);
        }
      }
    }
  }
}