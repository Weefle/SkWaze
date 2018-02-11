package fr.weefle.waze.effects;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;
import fr.weefle.waze.Waze;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectTitle extends Effect {
	
	private Expression<String> title;
	private Expression<String> subtitle;
	private Expression<Player> player;
	private Expression<Integer> time;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		title = (Expression<String>) arg0[0];
		subtitle = (Expression<String>) arg0[1];
		player = (Expression<Player>) arg0[2];
		time = (Expression<Integer>) arg0[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send title to player";
	}

	@Override
	protected void execute(Event arg0) {
		// TODO Auto-generated method stub
        try {
			Waze.getInstance().getTitle().sendTitle(player.getSingle(arg0), title.getSingle(arg0), subtitle.getSingle(arg0), time.getSingle(arg0));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
