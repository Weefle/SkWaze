package fr.weefle.waze.skwrapper.effects;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;

public class WazeEffectStartAllServers extends Effect{

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "start all servers with SkWrapper";
	}

	@Override
	protected void execute(Event arg0) {
		
		PluginMessage pm = new PluginMessage("SkWrapper-start-all");
		Waze.getComApi().sendMessage(pm);
		
	}

}
