package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectBossBarCreate extends Effect {
	
	private Expression<String> message;
	private Expression<Integer> percent;
	private Expression<String> color;
	private Expression<String> style;
	private Expression<String> id;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		message = (Expression<String>) arg0[0];
		percent = (Expression<Integer>) arg0[1];
		color = (Expression<String>) arg0[2];
		style = (Expression<String>) arg0[3];
		id = (Expression<String>) arg0[4];
		player = (Expression<Player>) arg0[5];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send bossbar to player";
	}

	@Override
	protected void execute(Event arg0) {
        	for(Player p : player.getAll(arg0)){
        		NMS.getInstance().getBossBar().sendBossBar(p, message.getSingle(arg0), percent.getSingle(arg0), color.getSingle(arg0), id.getSingle(arg0), style.getSingle(arg0));
        	}
			//Waze.getInstance().getBossBar().sendBossBar(player.getSingle(arg0), message.getSingle(arg0), percent.getSingle(arg0), color.getSingle(arg0), id.getSingle(arg0));
    }

}
