package fr.weefle.waze.legacy;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectBossBarRemoveOld extends Effect {
	
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
        player = (Expression<Player>) arg0[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "remove bossbar from player";
    }

    @Override
    protected void execute(Event arg0) {
    	for(Player p : player.getAll(arg0)){
    		NMS.getInstance().getBossBar().removeBossBar(p, null);
    	}
    		//Waze.getInstance().getBossBar().removeBossBar(player.getSingle(arg0), id.getSingle(arg0));
    }

}
