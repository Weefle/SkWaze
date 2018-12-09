package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectCreateHologram extends Effect {
	
	private Expression<String> message;
	private Expression<Location> location;
	private Expression<String> id;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		message = (Expression<String>) arg0[0];
		location = (Expression<Location>) arg0[1];
		id = (Expression<String>) arg0[2];
		player = (Expression<Player>) arg0[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "display hologram at location for player";
	}

	@Override
	protected void execute(Event arg0) {
	        	for(Location l : location.getAll(arg0)) {
	        		for(Player p : player.getAll(arg0)){
	        		NMS.getInstance().getHolograms().createHologram(message.getSingle(arg0), l, id.getSingle(arg0), p);
	        	
		}}
		
		
	}

}
