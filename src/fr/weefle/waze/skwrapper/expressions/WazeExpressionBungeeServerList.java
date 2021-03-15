package fr.weefle.waze.skwrapper.expressions;

import javax.annotation.Nullable;

import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountGlobal;
import me.dommi2212.BungeeBridge.packets.PacketGetServers;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.weefle.waze.skwrapper.BungeeReceiver;
import fr.weefle.waze.utils.NMS;

import java.util.ArrayList;

public class WazeExpressionBungeeServerList extends SimpleExpression<String> {

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
			int i;
			ArrayList<String> list = (ArrayList<String>) new PacketGetServers().send();
			StringBuilder sb = new StringBuilder();
			if(list.size()>1){
			for (i=0;i< list.size()-1;i++) {
				sb.append(list.get(i));
				sb.append(",");
			}
				sb.append(list.get(i));}
			else{
				sb.append(list.get(0));
			}
					return new String[]{sb.toString()};
	}
		}
