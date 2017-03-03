package fr.weefle.waze;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

public class WazeEffectBossBar extends Effect {
	
	private Expression<String> message;
	private Expression<Float> percent;
	private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		message = (Expression<String>) arg0[0];
		percent = (Expression<Float>) arg0[2];
		player = (Expression<Player>) arg0[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send bossbar to player";
	}

	@Override
	protected void execute(Event arg0) {
		// TODO Auto-generated method stub
       ViaAPI<?> api = Via.getAPI();
       BossBar<?> bar = api.createBossBar(message.getSingle(arg0), percent.getSingle(arg0), BossColor.RED, BossStyle.SOLID);
       bar.addPlayer(player.getSingle(arg0).getUniqueId());
	}

}
