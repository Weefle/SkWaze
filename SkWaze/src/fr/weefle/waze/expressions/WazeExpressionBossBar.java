package fr.weefle.waze.expressions;

import javax.annotation.Nullable;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;

public class WazeExpressionBossBar extends SimpleExpression<BossBar> {
	
	private Expression<String> id;
	private Expression<Player> player;

	@Override
	public Class<? extends BossBar> getReturnType() {
		// TODO Auto-generated method stub
		return BossBar.class;
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
		id = (Expression<String>) arg0[0];
		player = (Expression<Player>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "ping of player";
	}

	@Override
	@Nullable
	protected BossBar[] get(Event arg0) {
			for(Player p : player.getAll(arg0)){
				return new BossBar[]{ Waze.getInstance().getBossBar().getBossBar(p, id.getSingle(arg0)) };
        	}
			return null;

	
	

}}
