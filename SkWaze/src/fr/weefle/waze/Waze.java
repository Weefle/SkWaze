package fr.weefle.waze;

import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;

public class Waze extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		Skript.registerAddon(this);
		Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [subtitle] %string% to %player% for %integer% tick[s]");
		super.onEnable();
	}

}
