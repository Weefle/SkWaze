package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.util.UUID;

import fr.weefle.waze.Waze;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Manages the Config.
 */
public class ConfigManager {
	
	public static void createConfig() {
		try {
			Waze.configfile.createNewFile();
			FileConfiguration config = YamlConfiguration.loadConfiguration(Waze.configfile);
			String pass = UUID.randomUUID().toString().replace("-", "");
			config.set("configversion", Waze.getVersion());
			config.set("host", "localhost");
			config.set("port", 7331);
			config.set("securitymode", "OFF");
			config.set("pass", pass.substring(pass.length()-10, pass.length()));
			config.set("updateinterval", 2);
			config.set("packetlogger", true);
			config.set("notify-bungee.chat", true);
			config.set("notify-bungee.command", true);
			Waze.config = config;
			config.save(Waze.configfile);
			loadConfig();
		} catch (IOException e) {
			ConsolePrinter.err("Failed to load/create config.yml!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(Waze.instance);
		}
	}
	
	public static void loadConfig() {
		Waze.configversion = Waze.config.getInt("configversion");
		Waze.host = Waze.config.getString("host");
		Waze.port = Waze.config.getInt("port");
		Waze.secmode = SecurityMode.valueOf(Waze.config.getString("securitymode").toUpperCase());
		Waze.pass = Waze.config.getString("pass");
		int updateinterval = Waze.config.getInt("updateinterval");
		if(updateinterval > 0) {
			Waze.updateinterval = updateinterval;
		} else {
			ConsolePrinter.warn("Illegal UpdateInterval! Using default-value (2)!");
			Waze.updateinterval = 2;
		}
		Waze.loggerenabled = Waze.config.getBoolean("packetlogger");
		Waze.notifybungeeChat = Waze.config.getBoolean("notify-bungee.chat");
		Waze.notifybungeeCommand = Waze.config.getBoolean("notify-bungee.command");
		
	}

}
