package fr.weefle.waze.expressions;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;
import fr.weefle.waze.utils.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class WazeExpressionPing extends SimpleExpression<Integer> {
	
	private Expression<Player> player;

	@Override
	public Class<? extends Integer> getReturnType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		player = (Expression<Player>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "ping of player";
	}

	@Override
	@Nullable
	protected Integer[] get(Event arg0) {
		// TODO Auto-generated method stub
		try {
			for(Player p : player.getAll(arg0)){
				return new Integer[]{ NMS.getInstance().getPing().getPing(p) };
        	}
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
