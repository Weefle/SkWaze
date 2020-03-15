package fr.weefle.waze;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.weefle.waze.data.ComApiBukkitHandler;
import fr.weefle.waze.utils.Metrics;
import fr.weefle.waze.utils.NMS;
import fr.weefle.waze.utils.Updater;

public class Waze extends JavaPlugin {
	
	public static Waze instance;
	private ComApiBukkitHandler handler;
	
	@Override
	public void onEnable() {
		instance = this;
		handler = new ComApiBukkitHandler(instance, "SkWrapper");
		//new DiscordRegister();
		//Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		NMS nms = new NMS();
			new Metrics(this);
			getLogger().info("Metrics setup was successful!");
		try {
			new Updater(this, 49195);
			getLogger().info("Updater setup was successful!");
		} catch (IOException e) {
			getLogger().severe("Failed to setup Updater!");
			getLogger().severe("Verify the resource's link!");
			e.printStackTrace();
		}
		if (nms.isSet()) {

			getLogger().info("NMS setup was successful!");
			getLogger().info("The plugin setup process is complete!");

		} else {

			getLogger().severe("Failed to setup NMS!");
			getLogger().severe("Your server version is not compatible with this plugin!");

			Bukkit.getPluginManager().disablePlugin(this);
		}
        }
	
	public static ComApiBukkitHandler getComApi() {
		return instance.handler;
		
	}

	public static Waze getInstance() {
		return instance;
	}

}