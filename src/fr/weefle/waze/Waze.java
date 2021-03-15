package fr.weefle.waze;

import java.io.File;
import java.io.IOException;

import fr.weefle.waze.utils.SerializableManager;
import me.dommi2212.BungeeBridge.*;
import me.dommi2212.BungeeBridge.events.listeners.ListenerChat;
import me.dommi2212.BungeeBridge.events.listeners.ListenerCommand;
import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;
import me.dommi2212.BungeeBridge.util.ServerRunningResult;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import fr.weefle.waze.utils.Metrics;
import fr.weefle.waze.utils.NMS;
import fr.weefle.waze.utils.Updater;

public class Waze extends JavaPlugin {
	
	public static Waze instance;
	public SerializableManager serializableManager;

	/** The unique bungeename of this server. Obtained by sending PacketServerRunning. */
	public static String bungeename = "";

	/** The version of the config. Obtained from the config. */
	public static int configversion;

	/** The host to send packets to. Obtained from the config. */
	public static String host;

	/** The port to send packets to. Obtained from the config. */
	public static int port;

	/** The SecurityMode of this client. Obtained from the config. */
	public static SecurityMode secmode;

	/** The password used to secure packets. Obtained from the config. */
	public static String pass;

	/** The interval in seconds to send PacketKeepAlives automatically. Obtained from the config. */
	public static int updateinterval;

	/** Whether packetlogger is enabled or not. Obtained from the config. */
	public static boolean loggerenabled;

	/** Whether AsyncPlayerChatEvent should be passed to Bungeecord. Obtained from the config. */
	public static boolean notifybungeeChat;

	/** Whether PlayerCommandPreprocessEvent should be passed to Bungeecord. Obtained from the config. */
	public static boolean notifybungeeCommand;

	/** The File of the config. */
	public static File configfile;

	/** The FileConfiguration of the config. */
	public static FileConfiguration config;
	
	@Override
	public void onEnable() {
		ConsolePrinter.print("Starting Socket System... Keep in mind you always have to use the same version of SkWrapper(Bungeecord) and SkWaze(Spigot)!");
		instance = this;
		serializableManager = new SerializableManager();
		enable();
		this.getCommand("packetmanager").setExecutor(new CommandPacketManager());
		registerListeners();

		ConsolePrinter.print("Port: " + port);
		ConsolePrinter.print("SecurityMode: " + secmode);

		long sended = System.currentTimeMillis();
		PacketServerRunning startpacket = new PacketServerRunning(Bukkit.getMotd(), Bukkit.getPort(), updateinterval, getVersion(), Bukkit.getMaxPlayers());
		ServerRunningResult result = (ServerRunningResult) startpacket.send();

		if(result.getErrorCode() == 0) {
			bungeename = result.getBungeename();
			ConsolePrinter.print("Connected! Server ---[" + (result.getTime() - sended) + "ms]---> Bungee ---[" + (System.currentTimeMillis() - result.getTime()) + "ms]---> Server");
			Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
				@Override
				public void run() {
					int errorCode = (int) new PacketKeepAlive(bungeename, true, Bukkit.getMotd()).send();
					if(errorCode != 0) {
						fixKeepAlive(errorCode);
					}
				}
			}, updateinterval * 20L, updateinterval * 20L);
		} else {
			handleServerRunningError(result.getErrorCode());
		}
		//new DiscordRegister("token");
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

	@Override
	public void onDisable() {
		PacketServerStopping stoppacket = new PacketServerStopping(bungeename);
		stoppacket.send();
		instance = null;
	}

	private void enable() {
		if(!Waze.instance.getDataFolder().exists()) {
			Waze.instance.getDataFolder().mkdir();
		}
		configfile = new File(Waze.instance.getDataFolder().getPath() + File.separator + "config-socket.yml");
		if(configfile.exists()) ConfigUpdater.update();
		else ConfigManager.createConfig();
		ConfigManager.loadConfig();
	}

	private void registerListeners() {
		if(notifybungeeChat) Bukkit.getPluginManager().registerEvents(new ListenerChat(), this);
		if(notifybungeeCommand) Bukkit.getPluginManager().registerEvents(new ListenerCommand(), this);
	}

	private static void fixKeepAlive(int errorCode) {
		ConsolePrinter.warn("An error occurred with code " + errorCode + " whilst sending a PacketKeepAlive! Trying to fix it...");
		switch(errorCode) {
			case 1: //Resent PacketServerRunning
				PacketServerRunning fixPacket = new PacketServerRunning(Bukkit.getMotd(), Bukkit.getPort(), updateinterval, getVersion(), Bukkit.getMaxPlayers());
				ServerRunningResult fixResult = (ServerRunningResult) fixPacket.send();
				if(fixResult.getErrorCode() == 0) {
					ConsolePrinter.print("Fixed!");
				} else {
					handleServerRunningError(fixResult.getErrorCode());
				}
				break;
			default: //Should never happen...
				ConsolePrinter.warn("Unknown error!");
				break;
		}
	}

	private static void handleServerRunningError(int errorCode) {
		switch (errorCode) {
			case 1: //Invalid version
				ConsolePrinter.err("Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\nYou have to update immediately! Shutting down BungeeBridgeClient...");
				break;
			case 2: //Unknown server
				ConsolePrinter.err("This server hasn't been registered in config.yml of Bungeecord! Shutting down BungeeBridgeClient...");
				break;
			default: //Should never happen...
				ConsolePrinter.warn("An unknown error occurred, whilst sending PacketServerRunning! Shutting down BungeeBridgeClient...");
				break;
		}
		Bukkit.getPluginManager().disablePlugin(instance);
	}

	/**
	 * Manually sends a PacketKeepAlive to BungeeBridgeS.
	 * Use this to increase the accuracy of PacketGetMOTDServer after setting the MOTD.
	 * The return-value is just used to signal the result of the operation. Any error is already handled by BungeeBridgeC.
	 *
	 * @return The error-code, if an error occurred; else 0
	 */
	public static int sendKeepAlive() {
		int errorCode = (int) new PacketKeepAlive(bungeename, false, Bukkit.getMotd()).send();
		if(errorCode != 0) {
			fixKeepAlive(errorCode);
		}
		return errorCode;
	}

	/**
	 * Gets the version of BungeeBridgeC.
	 *
	 * @return The version of BungeeBridgeC.
	 */
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}

	/**
	 * Gets the bungeename of this server.
	 *
	 * @return The bungeename of this server.
	 */
	public static String getBungeename() {
		return bungeename;
	}

	/**
	 * Gets the host of BungeeBridgeS.
	 *
	 * @return The host of BungeeBridgeS.
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * Gets the port of BungeeBridgeS.
	 *
	 * @return The port of BungeeBridgeS.
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Gets the security mode.
	 *
	 * @return The security mode.
	 */
	public static SecurityMode getSecurityMode() {
		return secmode;
	}

	/**
	 * Gets the password used to authenticate with BungeeBridgeS.
	 *
	 * @return The password used to authenticate with BungeeBridgeS.
	 */
	public static String getPass() {
		return pass;
	}

	/**
	 * Gets the interval in seconds to send PacketKeepAlives automatically.
	 *
	 * @return The updateinterval in seconds.
	 */
	public static int getUpdateInterval() {
		return updateinterval;
	}

	/**
	 * Checks if the packet-logger is enabled.
	 *
	 * @return true, if the packet-logger is enabled
	 */
	public static boolean isLoggerEnabled() {
		return loggerenabled;
	}

	public static Waze getInstance() {
		return instance;
	}

	public SerializableManager getSerializableManager(){
		return this.serializableManager;
	}

}