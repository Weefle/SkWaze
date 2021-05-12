package fr.weefle.waze.skwrapper.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketKickAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketKickPlayer;
import me.dommi2212.BungeeBridge.packets.PacketMessagePlayer;
import me.dommi2212.BungeeBridge.packets.PacketSendActionbar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeEffectSendKick extends Effect {

    private Expression<String> msg;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
        player = (Expression<Player>) arg0[0];
        msg = (Expression<String>) arg0[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "kick player on proxy";
    }

    @Override
    protected void execute(Event arg0) {
        for(Player p : player.getAll(arg0)){
            new PacketKickPlayer(p.getUniqueId(), msg.getSingle(arg0)).send();
        }

    }

}
