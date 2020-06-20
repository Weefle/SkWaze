package fr.weefle.waze.effects;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;
import fr.weefle.waze.utils.NMS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectActionBar extends Effect {
	
	private Expression<String> message;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		message = (Expression<String>) arg0[0];
		player = (Expression<Player>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "send actionbar to player";
	}

	@Override
	protected void execute(Event arg0) {
        try {
        	for(Player p : player.getAll(arg0)){
        		NMS.getInstance().getActionbar().sendActionBar(p, message.getSingle(arg0));
        	}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e) {
			e.printStackTrace();
		}
    }

}
