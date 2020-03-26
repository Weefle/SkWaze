package fr.weefle.waze.skwrapper.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.Waze;
import fr.weefle.waze.data.PluginMessage;
import fr.weefle.waze.data.PluginMessageRequest;
import fr.weefle.waze.skwrapper.BungeeReceiver;

public class WazeExpressionPlayerServer extends SimpleExpression<String> {
	
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

		@Override
		public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
			player = (Expression<Player>) arg0[0];
			return true;
		}

		@Override
		public String toString(@Nullable Event arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return "bungee player server";
		}

		@Override
		@Nullable
		protected String[] get(Event arg0) {	
	
						return new String[]{ BungeeReceiver.servers.get(player.getSingle(arg0)) };

	}
		}
