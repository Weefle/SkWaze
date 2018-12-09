package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectAddLineHologram extends Effect {
	
	private Expression<Integer> line;
	private Expression<String> id;
	private Expression<String> msg;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		line = (Expression<Integer>) arg0[0];
		id = (Expression<String>) arg0[1];
		msg = (Expression<String>) arg0[2];
		player = (Expression<Player>) arg0[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "add line with text at hologram for player";
	}

	@Override
	protected void execute(Event arg0) {
		for(Player p : player.getAll(arg0)){
	        		NMS.getInstance().getHolograms().addLineHologram(id.getSingle(arg0), msg.getSingle(arg0), line.getSingle(arg0), p);
		
		
		}
	}

}
