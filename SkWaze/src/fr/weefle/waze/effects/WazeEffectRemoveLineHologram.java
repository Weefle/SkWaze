package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectRemoveLineHologram extends Effect {

	private Expression<String> id;
	private Expression<Player> player;
	private Expression<Integer> line;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		line = (Expression<Integer>) arg0[0];
		id = (Expression<String>) arg0[1];
		player = (Expression<Player>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "remove all lines from hologram for player";
	}

	@Override
	protected void execute(Event arg0) {
		for(Player p : player.getAll(arg0)){
	        		NMS.getInstance().getHolograms().removeLineHologram(id.getSingle(arg0), line.getSingle(arg0), p);
		
		}
		
	}

}
