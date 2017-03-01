package fr.weefle.waze;

import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class Waze extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		Skript.registerAddon(this);
		Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %player% (for|to) %integer% tick[s]");
		Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %player%");
		Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %player%['s] ping", "[waze] ping of %player%");
		Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
		Skript.registerEffect(WazeEffectClearRecipe.class, "[waze] (remove|clear) [craft[ing]] recipe[s] (for|to) %itemtypes%");
		super.onEnable();
	}

}
