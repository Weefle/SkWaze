package fr.weefle.waze.skwrapper;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeExpressionBungeeOnlineCountGlobal extends SimpleExpression<Integer> {

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

		@Override
		public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
			return true;
		}

		@Override
		public String toString(@Nullable Event arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return "global bungee online count";
		}

		@Override
		@Nullable
		protected Integer[] get(Event arg0) {
					return new Integer[]{ NMS.getInstance().getBungee().onlineGlobal };
	}
		}
