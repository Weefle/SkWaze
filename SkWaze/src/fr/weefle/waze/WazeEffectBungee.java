package fr.weefle.waze;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
public class WazeEffectBungee extends Effect {
	
	private Expression<Player> p;
	private Expression<String> srv;

protected void execute(Event event)
{
  Player p = (Player)this.p.getSingle(event);
  String srv = (String)this.srv.getSingle(event);
  if ((p == null) || (srv == null)) {
    return;
  }
  connect(p, srv);
}

public static void connect(Player p, String srv)
{
  ByteArrayOutputStream b = new ByteArrayOutputStream();
  DataOutputStream out = new DataOutputStream(b);
  try
  {
    out.writeUTF("Connect");
    out.writeUTF(srv);
  }
  catch (IOException localIOException) {}
  p.sendPluginMessage(Waze.getInstance(), "BungeeCord", b.toByteArray());
}

public String toString(Event event, boolean bool)
{
  return getClass().getName();
}

@SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
{
  this.p = (Expression<Player>) expressions[0];
  this.srv = (Expression<String>) expressions[1];
  return true;
}
}