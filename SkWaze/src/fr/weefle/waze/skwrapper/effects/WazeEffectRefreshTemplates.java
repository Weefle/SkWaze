package fr.weefle.waze.skwrapper.effects;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;

public class WazeEffectRefreshTemplates extends Effect {

protected void execute(Event event)
{
	PluginMessage pm = new PluginMessage("SkWrapper-refresh");
	Waze.getComApi().sendMessage(pm);
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