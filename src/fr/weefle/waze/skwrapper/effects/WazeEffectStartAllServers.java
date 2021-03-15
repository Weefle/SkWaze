package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

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

		PacketCustom packet = new PacketCustom("SkWrapper-start-all", null);
		packet.send();
		
	}

}
