package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.PackedTitle;
import me.dommi2212.BungeeBridge.packets.PacketSendTitle;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeEffectSendTitle extends Effect {

    private Expression<String> title;
    private Expression<String> subtitle;
    private Expression<Player> player;
    private Expression<Integer> time;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
        title = (Expression<String>) arg0[0];
        subtitle = (Expression<String>) arg0[1];
        player = (Expression<Player>) arg0[2];
        time = (Expression<Integer>) arg0[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "send title to player on proxy";
    }

    @Override
    protected void execute(Event arg0) {
            for(Player p : player.getAll(arg0)){
                new PacketSendTitle(p.getUniqueId(), new PackedTitle(title.getSingle(arg0), subtitle.getSingle(arg0), 20, time.getSingle(arg0)*20, 20)).send();
            }

    }

}
