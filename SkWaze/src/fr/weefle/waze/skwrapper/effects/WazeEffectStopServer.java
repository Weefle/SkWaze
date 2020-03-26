package fr.weefle.waze.skwrapper.effects;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.skwrapper.SkWrapper;

public class WazeEffectStopServer extends Effect{
	
	private Expression<String> server;
	private Expression<String> template;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		server = (Expression<String>) arg0[0];
		template = (Expression<String>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "stop a server with SkWrapper";
	}

	@Override
	protected void execute(Event arg0) {
		
		SkWrapper.stopServer(server.getSingle(arg0), template.getSingle(arg0));
		
	}

}
