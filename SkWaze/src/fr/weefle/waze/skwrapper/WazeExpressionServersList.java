package fr.weefle.waze.skwrapper;

import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class WazeExpressionServersList extends SimpleExpression<String> {
	
	private Expression<String> template;

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
		template = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "servers list from template";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
				return new String[]{ SkWrapper.getServers(template.getSingle(arg0)) };

}
}
