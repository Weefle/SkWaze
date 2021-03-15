package me.dommi2212.BungeeBridge;

import java.io.IOException;

import fr.weefle.waze.Waze;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Updates the config from an older version.
 */
public class ConfigUpdater {
	
	/**
	 * Update a config.
	 */
	public static void update() {
		final int CURRENTVERSION = Waze.getVersion();
		Waze.config = YamlConfiguration.loadConfiguration(Waze.configfile);
		if(Waze.config.get("configversion") == null) {
			//Update Config from 1.3.0 or lower
			ConsolePrinter.print("Your config is outdated! Running updater...");
			int oldInterval = Waze.config.getInt("updateintervall");
			Waze.config.set("updateintervall", null);
			Waze.config.set("updateinterval", oldInterval);
			
			Waze.config.set("packetlogger", true);
			Waze.config.set("notify-bungee.chat", true);
			Waze.config.set("notify-bungee.command", true);
			ConsolePrinter.print("Added 2 option(s)!");
			ConsolePrinter.print("Done!");
		} else {
			Waze.configversion = Waze.config.getInt("configversion");
			if(Waze.configversion < CURRENTVERSION) {
				ConsolePrinter.print("Your config is outdated! Running updater...");
				int added = 0;
				if(Waze.configversion <= 140) {
					Waze.config.set("packetlogger", true);
					added++;
				}
				if(Waze.configversion <= 151) {
					int oldInterval = Waze.config.getInt("updateintervall");
					Waze.config.set("updateintervall", null);
					Waze.config.set("updateinterval", oldInterval);
					
					Waze.config.set("notify-bungee.chat", true);
					Waze.config.set("notify-bungee.command", true);
					added = added + 2;
				}
				ConsolePrinter.print("Added " + added + " option(s)!");
				ConsolePrinter.print("Done!");
			}
		}
		Waze.config.set("configversion", Waze.getVersion());
		try {
			Waze.config.save(Waze.configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
