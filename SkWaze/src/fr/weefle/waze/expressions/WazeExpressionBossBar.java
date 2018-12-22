package fr.weefle.waze.expressions;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeExpressionBossBar extends SimpleExpression<String> {
	
	private Expression<Player> player;

	@Override
	public Class<? extends String> getReturnType() {
		// TODO Auto-generated method stub
		return String.class;
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
		return "bossbar list of player";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
			for(Player p : player.getAll(arg0)){
				return (String[]) NMS.getInstance().getBossBar().getBossBars(p);
        	}
			return null;

	
	

}}
