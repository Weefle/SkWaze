package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import me.dommi2212.BungeeBridge.packets.PacketStopProxy;

import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectStopProxy extends Effect {

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "stop proxy server";
	}

	@Override
	protected void execute(Event arg0) {

        		PacketStopProxy packet = new PacketStopProxy();
        		packet.send();
     
	}

}
