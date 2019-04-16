package fr.weefle.waze.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketConnectPlayer;

public class WazeEffectBungeeConnect extends Effect {
	
	private Expression<Player> player;
	private Expression<String> srv;

protected void execute(Event event)
{
	for(Player p : player.getAll(event)){
		  if ((p == null) || (srv.getSingle(event) == null)) {
		    return;
		  }
		  connect(p, srv.getSingle(event));
	}
}

public void connect(Player p, String srv)
{
	PacketConnectPlayer packet = new PacketConnectPlayer(p.getUniqueId(), srv);
	packet.send();
	
  /*ByteArrayOutputStream b = new ByteArrayOutputStream();
  DataOutputStream out = new DataOutputStream(b);
  try
  {
    out.writeUTF("Connect");
    out.writeUTF(srv);
  }
  catch (IOException localIOException) {}
  p.sendPluginMessage(Waze.getInstance(), "BungeeCord", b.toByteArray());*/
}

public String toString(Event event, boolean bool)
{
  return "send player to a bungeecord server";
}

@SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
{
  player = (Expression<Player>) expressions[0];
  srv = (Expression<String>) expressions[1];
  return true;
}
}