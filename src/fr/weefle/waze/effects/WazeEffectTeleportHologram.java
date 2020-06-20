package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectTeleportHologram extends Effect {
	
	private Expression<Location> location;
	private Expression<String> id;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		id = (Expression<String>) arg0[0];
		location = (Expression<Location>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "teleport hologram at location";
	}

	@Override
	protected void execute(Event arg0) {
	        	for(Location l : location.getAll(arg0)){
	        		NMS.getInstance().getHolograms().teleportHologram(id.getSingle(arg0), l);
	        		
	        	
		}
		
		
	}

}
