package fr.weefle.waze;

import fr.weefle.waze.discord.DiscordRegister;
import fr.weefle.waze.nms.*;
import fr.weefle.waze.old.ActionBarOld;
import fr.weefle.waze.old.AutoRespawnOld;
import fr.weefle.waze.old.BossBarOld;
import fr.weefle.waze.old.ParticleOld;
import fr.weefle.waze.utils.Metrics;
import fr.weefle.waze.utils.Register;
import fr.weefle.waze.utils.Updater;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Waze extends JavaPlugin {
	
	private static Waze instance;
	private ActionBarAPI actionbar;
	private Title title;
	private BossBarAPI bossbar;
	private Ping ping;
	private Tablist tablist;
	private ParticleAPI particle;
	private Nametag nametag;
	private ScoreBoard scoreboard;
	private AutoRespawnAPI autorespawn;
	private DiscordRegister discord;
	//private Bot bot;

	@Override
	public void onEnable() {
		/*Classes.registerClass(new ClassInfo<BossBarNew>(BossBarNew.class, "bossbar").user("bossbar").name("bossbar").parser(new Parser<BossBarNew>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public BossBarNew parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(BossBarNew arg0, int arg1) {
				return null;
			}

			@Override
			public String toVariableNameString(BossBarNew arg0) {
				return null;
			}
		   
		}));*/
		/*if(getServer().getPluginManager().isPluginEnabled("Discord-ProgramBot-API")) {
			discord = new DiscordRegister(this);
			bot = new Bot("NDYxNTk3MzYyODcyMTIzMzkz.DhVocQ.px7FnBq7Z8XJw9vW97H0hriGenI", "[Wazea]");
			discord.initialiseBot(bot);
			//bot.addCommand(new PingCommand());
			getLogger().info("Discord setup was successful!");
		}else {
			getLogger().severe("Failed to setup Discord!");
		}*/
		getLogger().info("SkWrapper setup was successful!");
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
		if (setupNMS()) {

			getLogger().info("NMS setup was successful!");
			getLogger().info("The plugin setup process is complete!");

		} else {

			getLogger().severe("Failed to setup NMS!");
			getLogger().severe("Your server version is not compatible with this plugin!");

			Bukkit.getPluginManager().disablePlugin(this);
		}
		instance = this;
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		new Register(this);
        }

	private boolean setupNMS() {

		String version;

		try {

			version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

		} catch (ArrayIndexOutOfBoundsException exception) {
			return false;
		}

		getLogger().info("Your server is running version " + version);
		
		scoreboard = new ScoreBoard();
		ping = new Ping();
		nametag = new Nametag();
		tablist = new Tablist();
		
		if (version.equals("v1_8_R3") || version.equals("v1_8_R2") || version.equals("v1_8_R1")) {
        	particle = new ParticleOld();
        	title = new Title();
    		autorespawn = new AutoRespawnOld(this);
    		bossbar = new BossBarOld();
    		actionbar = new ActionBarOld();
        }else if (version.equals("v1_7_R4") || version.equals("v1_7_R3") || version.equals("v1_7_R2") || version.equals("v1_7_R1")){
        	particle = new ParticleOld();
    		autorespawn = new AutoRespawnOld(this);
    		bossbar = new BossBarOld();
        }else if(version.equals("v1_9_R1") || version.equals("v1_9_R2")) {
        	title = new Title();
        	particle = new ParticleNew();
        	autorespawn = new AutoRespawnNew();
    		bossbar = new BossBarNew(this);
    		actionbar = new ActionBarOld();
    }else {
    	title = new Title();
    	particle = new ParticleNew();
    	autorespawn = new AutoRespawnNew();
		bossbar = new BossBarNew(this);
		actionbar = new ActionBarNew();
    }
		return true;
	}
    public ActionBarAPI getActionbar() {
        return actionbar;
    }
    public Title getTitle() {
        return title;
    }
    public static Waze getInstance(){
	    return instance;
    }
    public BossBarAPI getBossBar(){
        return bossbar;
    }
    public Ping getPing(){
        return ping;
    }
    public ScoreBoard getScoreBoard(){
	    return scoreboard;
    }
    
    public AutoRespawnAPI getAutoRespawn(){
	    return autorespawn;
    }
    
    public Nametag getNametag(){
	    return nametag;
    }
    
    public Tablist getTablist(){
	    return tablist;
    }
    
    public ParticleAPI getParticles(){
	    return particle;
    }
    
    public DiscordRegister getDiscord() {
		return discord;
	}

}