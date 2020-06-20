package fr.weefle.waze.conditions;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeConditionHologram extends Condition{
	
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
		return "id of hologram exists";
	}

	@Override
	public boolean check(Event arg0) {
		if(NMS.getInstance().getHolograms().holo.containsKey(id.getSingle(arg0))) {
			return true;
		}else {
		return false;
		}
	}

}
