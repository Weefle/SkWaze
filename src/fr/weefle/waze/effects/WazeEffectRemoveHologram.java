package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectRemoveHologram extends Effect {
	
	private Expression<String> id;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {

		id = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "remove hologram";
	}

	@Override
	protected void execute(Event arg0) {
	        		NMS.getInstance().getHolograms().removeHologram(id.getSingle(arg0));
		
		
		
	}

}
