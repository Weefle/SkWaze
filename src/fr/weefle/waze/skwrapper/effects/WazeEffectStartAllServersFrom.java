package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeEffectStartAllServersFrom extends Effect{
	
	private Expression<String> template;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		template = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "start all servers from template with SkWrapper";
	}

	@Override
	protected void execute(Event arg0) {

		StringBuilder sb = new StringBuilder();
		sb.append(template.getSingle(arg0));
		String str = sb.toString();
		PacketCustom packet = new PacketCustom("SkWrapper-start-all-from", (Object) str);
		packet.send();
		
	}

}
