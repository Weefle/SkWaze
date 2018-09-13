package fr.weefle.waze;

import fr.weefle.waze.skwrapper.GetFromBungeeCord;
import fr.weefle.waze.skwrapper.PluginChannelListener;
import fr.weefle.waze.utils.Metrics;
import fr.weefle.waze.utils.NMS;
import fr.weefle.waze.utils.Updater;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Waze extends JavaPlugin {
	
	public static Waze instance;
	public static PluginChannelListener pcl;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // allow to send to BungeeCord
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "Return", pcl = new PluginChannelListener());
        // gets a Message from Bungee
 
        getCommand("get").setExecutor(new GetFromBungeeCord());
		NMS nms = new NMS(this);
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

	public static Waze getInstance() {
		return instance;
	}

}