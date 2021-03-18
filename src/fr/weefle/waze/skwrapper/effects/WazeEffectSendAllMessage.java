package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketMessageAllPlayers;
import org.bukkit.event.Event;

public class WazeEffectSendAllMessage extends Effect {
    
    private Expression<String> msg;

    protected void execute(Event event)
    {

            if (msg.getSingle(event) == null) {
                return;
            }
            new PacketMessageAllPlayers(msg.getSingle(event)).send();

    }

    public String toString(Event event, boolean bool)
    {
        return "send message to all players on proxy";
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
    {
        msg = (Expression<String>) expressions[0];
        return true;
    }
}