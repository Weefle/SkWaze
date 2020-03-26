package fr.weefle.waze.skwrapper.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.skwrapper.BungeeReceiver;
import fr.weefle.waze.utils.NMS;

public class WazeExpressionBungeeServerList extends SimpleExpression<String> {

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

		@Override
		public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
			return true;
		}

		@Override
		public String toString(@Nullable Event arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return "bungee server list";
		}

		@Override
		@Nullable
		protected String[] get(Event arg0) {
					return new String[]{ BungeeReceiver.serverList };
	}
		}
