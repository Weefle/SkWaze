package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import fr.weefle.waze.utils.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectSetSideBar extends Effect {

    private Expression<String> name;
    private Expression<String> score;
    private Expression<Integer> line;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
        name = (Expression<String>) arg0[0];;
        score = (Expression<String>) arg0[1];
        line = (Expression<Integer>) arg0[2];
        player = (Expression<Player>) arg0[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "set line sidebar for player";
    }

    @Override
    protected void execute(Event arg0) {
        // TODO Auto-generated method stub
    	for(Player p : player.getAll(arg0)){
    		NMS.getInstance().getSideBar().setSideBar(name.getSingle(arg0), p, score.getSingle(arg0), line.getSingle(arg0));
    	}
    }

}
