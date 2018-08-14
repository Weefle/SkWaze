package fr.weefle.waze;

import fr.weefle.waze.discord.DiscordRegister;
import fr.weefle.waze.effects.*;
import fr.weefle.waze.nms.*;
import fr.weefle.waze.old.ActionBarOld;
import fr.weefle.waze.old.AutoRespawnOld;
import fr.weefle.waze.old.BossBarOld;
import fr.weefle.waze.old.WazeEffectBossBarCreateOld;
import fr.weefle.waze.old.WazeEffectBossBarRemoveOld;
import fr.weefle.waze.old.WazeEffectBossBarTimerOld;
import fr.weefle.waze.skwrapper.SkWrapperReceiver;
import fr.weefle.waze.skwrapper.SkWrapperSender;
import fr.weefle.waze.skwrapper.WazeEffectCreateServer;
import fr.weefle.waze.skwrapper.WazeEffectStartServer;
import fr.weefle.waze.skwrapper.WazeEffectStopServer;
import fr.weefle.waze.skwrapper.WazeExpressionServersList;
import fr.weefle.waze.utils.Metrics;
import fr.weefle.waze.utils.Updater;
import fr.weefle.waze.utils.UpdaterListener;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.tjplaysnow.discord.object.Bot;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import fr.weefle.waze.events.PlayerJumpEvent;
import fr.weefle.waze.events.PlayerSwimEvent;
import fr.weefle.waze.expressions.WazeExpressionBossBar;
import fr.weefle.waze.expressions.WazeExpressionPing;

public class Waze extends JavaPlugin {
	
	private static Waze instance;
	private ActionBar actionbar;
	private Title title;
	private BossBarAPI bossbar;
	private Ping ping;
	private Tablist tablist;
	private Particles particles;
	private Nametag nametag;
	private ScoreBoard scoreboard;
	private AutoRespawn autorespawn;
	private DiscordRegister discord;
	private Bot bot;

	@Override
	public void onEnable() {
		if(getServer().getPluginManager().isPluginEnabled("Discord-ProgramBot-API")) {
			discord = new DiscordRegister(this);
			bot = new Bot("NDYxNTk3MzYyODcyMTIzMzkz.DhVocQ.px7FnBq7Z8XJw9vW97H0hriGenI", "[Wazea]");
			discord.initialiseBot(bot);
			//bot.addCommand(new PingCommand());
			getLogger().info("Discord setup was successful!");
		}else {
			getLogger().severe("Failed to setup Discord!");
		}
		scoreboard = new ScoreBoard();
		ping = new Ping();
		particles = new Particles();
		nametag = new Nametag();
		tablist = new Tablist();
		getServer().getPluginManager().registerEvents(new SkWrapperSender(), this);
		getServer().getPluginManager().registerEvents(new SkWrapperReceiver(), this);
		getServer().getPluginManager().registerEvents(new UpdaterListener(), this);
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
		PlayerJumpEvent.register(this);
		PlayerSwimEvent.register(this);
		Skript.registerAddon(this);
        Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %players% (for|to) %integer% second[s]");
		Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectBungee.class, "[waze] (send|teleport) %players% to [bungee[cord]] server %string%");
		Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %players%['s] ping", "[waze] ping of %players%");
		Skript.registerExpression(WazeExpressionBossBar.class, String.class, ExpressionType.PROPERTY, "[waze] %players%['s] [boss]bar [list]", "[waze] [boss]bar [list] of %players%");
		Skript.registerExpression(WazeExpressionServersList.class, String.class, ExpressionType.PROPERTY, "[waze] [skwrapper] servers list from [template] %string%", "[waze] list of [skwrapper] servers from [template] %string%", "[waze] [skwrapper] servers list from %string%['s] [template]", "[waze] list of [skwrapper] servers from %string%['s] [template]");
		Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
		Skript.registerEffect(WazeEffectClearRecipes.class, "[waze] (remove|clear|delete) [all] [craft[ing]] recipe[s]");
		Skript.registerEffect(WazeEffectBossBarCreate.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% with id %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarTimer.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% with id %string% (for|and) %integer% second[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarRemove.class, "[waze] (remove|delete|clear) [boss]bar with id %string% (of|for) %players%");
		Skript.registerEffect(WazeEffectBossBarCreateOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarTimerOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (for|and) %integer% second[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarRemoveOld.class, "[waze] 1.8 (remove|delete|clear) [boss]bar (of|for) %players%");
        Skript.registerEffect(WazeEffectScoreBoard.class, "[waze] (create|make) scoreboard %string% of type %string% to [display]slot %string% (with|and) score %string% (at|for) line %integer% (to|for) %players%");
		Skript.registerEffect(WazeEffectRemoveScoreBoard.class, "[waze] (clear|remove) scoreboard %string% (of|for) %players%");
		Skript.registerEffect(WazeEffectTablist.class, "[waze] (set|show) tab[list] (with|from) [head[er]] %string% (and|with) [foot[er]] %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectNametag.class, "[waze] (set|show) name[tag] %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectAutoRespawn.class, "[waze] [auto]respawn %players%");
		Skript.registerEffect(WazeEffectParticles.class, "[waze] (spawn|create|summon) [a number of] %integer% [of] %string%['s] particle[s] (to|for) %players% (at|from) %locations% (and|with) offset %float%, %float%, %float% (and|with) data %float%");
		Skript.registerEffect(WazeEffectCreateServer.class, "[waze] (add|create) [[a] new] [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStartServer.class, "[waze] (start|begin) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStopServer.class, "[waze] (stop|end) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEvent("Jump Event", SimpleEvent.class, PlayerJumpEvent.class, "[waze] jump[ing]");
		Skript.registerEvent("Swim Event", SimpleEvent.class, PlayerSwimEvent.class, "[waze] swim[ing]");
        /*EventValues.registerEventValue(PlayerJumpEvent.class, Player.class, new Getter<Player, PlayerJumpEvent>() {
            @Override
            public Player get(PlayerJumpEvent playerJumpEvent) {
                return playerJumpEvent.getPlayer();
            }
        }, 0);*/
        }

	private boolean setupNMS() {

		String version;

		try {

			version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

		} catch (ArrayIndexOutOfBoundsException exception) {
			return false;
		}

		getLogger().info("Your server is running version " + version);
		if (version.equals("v1_12_R1")) {
			title = new Title();
			autorespawn = new AutoRespawnNew();
			bossbar = new BossBarNew(this);
			actionbar = new ActionBarNew();

        } else if (version.equals("v1_8_R3")) {
        	title = new Title();
    		autorespawn = new AutoRespawnOld(this);
    		bossbar = new BossBarOld();
    		actionbar = new ActionBarOld();
        }else if (version.equals("v1_7_R4")){
    		autorespawn = new AutoRespawnOld(this);
    		bossbar = new BossBarOld();
    }else {
    	title = new Title();
    	autorespawn = new AutoRespawnNew();
		bossbar = new BossBarNew(this);
		actionbar = new ActionBarOld();
    }
		return true;
	}
    public ActionBar getActionbar() {
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
    
    public AutoRespawn getAutoRespawn(){
	    return autorespawn;
    }
    
    public Nametag getNametag(){
	    return nametag;
    }
    
    public Tablist getTablist(){
	    return tablist;
    }
    
    public Particles getParticles(){
	    return particles;
    }
    
    public DiscordRegister getDiscord() {
		return discord;
	}

}