package fr.weefle.waze;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.weefle.events.EvtJump;
import fr.weefle.waze.effects.WazeEffectActionBar;
import fr.weefle.waze.effects.WazeEffectBossBar;
import fr.weefle.waze.effects.WazeEffectClearRecipes;
import fr.weefle.waze.effects.WazeEffectRecipe;
import fr.weefle.waze.effects.WazeEffectTitle;
import fr.weefle.waze.expressions.WazeExpressionPing;

public class Waze extends JavaPlugin {
	
	public static Waze instance;
	
	@Override
	public void onEnable() {
		instance = this;
		Skript.registerAddon(this);
		Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %player% (for|to) %integer% tick[s]");
		Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %player%");
		Skript.registerEffect(WazeEffectBungee.class, "[waze] (send|teleport) %player% to [bungee[cord]] server %string%");
		Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %player%['s] ping", "[waze] ping of %player%");
		Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
		Skript.registerEffect(WazeEffectClearRecipes.class, "[waze] (remove|clear) [all] [craft[ing]] recipe[s]");
		Skript.registerEffect(WazeEffectBossBar.class, "[waze] (create|send) [boss]bar %string% (with|at) %float% percent[s] (to|for) %player%");
		Skript.registerEvent("Jump Event", SimpleEvent.class, EvtJump.class, "[waze] jump");
		EventValues.registerEventValue(EvtJump.class, Player.class, new Getter<Player, EvtJump>() {
		    public Player get(EvtJump e) {
		        return e.getPlayer();
		    }
		}, 0);
}

	public static Plugin getInstance() {
		return instance;
	}
}
