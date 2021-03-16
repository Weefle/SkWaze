package fr.weefle.waze.skwrapper.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class WazeExpressionBungeeOnlineCountGlobal extends SimpleExpression<Integer> {

		@Override
		public Class<? extends Integer> getReturnType() {
			// TODO Auto-generated method stub
			return Integer.class;
		}

		@Override
		public boolean isSingle() {
			// TODO Auto-generated method stub
			return true;
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
					return new Integer[]{(Integer)new PacketGetOnlineCountGlobal().send()};
	}
		}
