package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketMessageAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketRunCommand;
import org.bukkit.event.Event;

public class WazeEffectRunProxyCommand extends Effect {

    private Expression<String> cmd;

    protected void execute(Event event)
    {

        if (cmd.getSingle(event) == null) {
            return;
        }
        new PacketRunCommand(cmd.getSingle(event)).send();

    }

    public String toString(Event event, boolean bool)
    {
        return "run proxy command";
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, ParseResult parseResult)
    {
        cmd = (Expression<String>) expressions[0];
        return true;
    }
}