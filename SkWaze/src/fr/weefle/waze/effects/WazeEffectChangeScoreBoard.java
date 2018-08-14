package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;

public class WazeEffectChangeScoreBoard extends Effect {

	private Expression<String> slot;
    private Expression<String> score;
    private Expression<Integer> line;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        // TODO Auto-generated method stub
    	slot = (Expression<String>) arg0[0];
        score = (Expression<String>) arg0[1];
        line = (Expression<Integer>) arg0[2];
        player = (Expression<Player>) arg0[3];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return "send scoreboard to player";
    }

    @Override
    protected void execute(Event arg0) {
        // TODO Auto-generated method stub
    	for(Player p : player.getAll(arg0)){
    		Waze.getInstance().getScoreBoard().changeScore(slot.getSingle(arg0), p, score.getSingle(arg0), line.getSingle(arg0));
    	}
    }

}
