package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import org.bukkit.event.Event;

public class WazeEffectRefreshTemplates extends Effect {

protected void execute(Event event)
{
    PacketCustom packet = new PacketCustom("SkWrapper-refresh", null);
    packet.send();
}

public String toString(Event event, boolean bool)
{
  return "refresh skwrapper server templates";
}

public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
{

  return true;
}
}