package fr.weefle.waze.skwrapper.expressions;

import javax.annotation.Nullable;

import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;
import me.dommi2212.BungeeBridge.packets.PacketGetServerByPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.skwrapper.BungeeReceiver;

import java.util.Objects;

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
			return true;
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
	
						return new String[]{(String)new PacketGetServerByPlayer(Objects.requireNonNull(player.getSingle(arg0)).getUniqueId()).send() };

	}
		}
