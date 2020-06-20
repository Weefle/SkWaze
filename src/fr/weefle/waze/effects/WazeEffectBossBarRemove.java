package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import fr.weefle.waze.utils.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectBossBarRemove extends Effect {
	
	private Expression<String> id;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
    	id = (Expression<String>) arg0[0];
        player = (Expression<Player>) arg0[1];
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
    		NMS.getInstance().getBossBar().removeBossBar(p, id.getSingle(arg0));
    	}
    		//Waze.getInstance().getBossBar().removeBossBar(player.getSingle(arg0), id.getSingle(arg0));
    }

}
