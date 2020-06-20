package fr.weefle.waze.effects;

import javax.annotation.Nullable;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectDisguiseMob extends Effect {
	
	private Expression<Player> player;
	private Expression<String> message;
	private Expression<Boolean> viewitself;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) arg0[0];
		message = (Expression<String>) arg0[1];
		viewitself = (Expression<Boolean>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "disguise player as mob";
	}

	@Override
	protected void execute(Event arg0) {
        	for(Player p : player.getAll(arg0)){
        		MobDisguise dis = new MobDisguise(DisguiseType.valueOf(message.getSingle(arg0)));
        		DisguiseAPI.disguiseEntity(p, dis);
        		dis.setViewSelfDisguise(viewitself.getSingle(arg0));
        	}

    }

}
