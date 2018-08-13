package fr.weefle.waze.effects;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;

public class WazeEffectTablist extends Effect {
	
	private Expression<String> header;
	private Expression<String> footer;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		header = (Expression<String>) arg0[0];
		footer = (Expression<String>) arg0[1];
		player = (Expression<Player>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send title to player";
	}

	@Override
	protected void execute(Event arg0) {
        	for(Player p : player.getAll(arg0)){
        		try {
					Waze.getInstance().getTablist().setTablist(header.getSingle(arg0), footer.getSingle(arg0), p);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e) {
					e.printStackTrace();
				};
        	}
	}

}
