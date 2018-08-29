package fr.weefle.waze.utils;

import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import com.tjplaysnow.discord.object.Bot;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.weefle.waze.Waze;
import fr.weefle.waze.discord.DiscordRegister;
import fr.weefle.waze.effects.WazeEffectActionBar;
import fr.weefle.waze.effects.WazeEffectAutoRespawn;
import fr.weefle.waze.effects.WazeEffectBossBarCreate;
import fr.weefle.waze.effects.WazeEffectBossBarRemove;
import fr.weefle.waze.effects.WazeEffectBossBarTimer;
import fr.weefle.waze.effects.WazeEffectBuilder;
import fr.weefle.waze.effects.WazeEffectBungee;
import fr.weefle.waze.effects.WazeEffectClearRecipes;
import fr.weefle.waze.effects.WazeEffectDisguiseMisc;
import fr.weefle.waze.effects.WazeEffectDisguiseMob;
import fr.weefle.waze.effects.WazeEffectDisguisePlayer;
import fr.weefle.waze.effects.WazeEffectNametag;
import fr.weefle.waze.effects.WazeEffectParticles;
import fr.weefle.waze.effects.WazeEffectRecipe;
import fr.weefle.waze.effects.WazeEffectRemoveScoreBoard;
import fr.weefle.waze.effects.WazeEffectScoreBoard;
import fr.weefle.waze.effects.WazeEffectTablist;
import fr.weefle.waze.effects.WazeEffectTitle;
import fr.weefle.waze.events.PlayerJumpEvent;
import fr.weefle.waze.events.PlayerSwimEvent;
import fr.weefle.waze.expressions.WazeExpressionBossBar;
import fr.weefle.waze.expressions.WazeExpressionPing;
import fr.weefle.waze.nms.ActionBarAPI;
import fr.weefle.waze.nms.ActionBarNew;
import fr.weefle.waze.nms.AutoRespawnAPI;
import fr.weefle.waze.nms.AutoRespawnNew;
import fr.weefle.waze.nms.BossBarAPI;
import fr.weefle.waze.nms.BossBarNew;
import fr.weefle.waze.nms.Nametag;
import fr.weefle.waze.nms.ParticleAPI;
import fr.weefle.waze.nms.ParticleNew;
import fr.weefle.waze.nms.Ping;
import fr.weefle.waze.nms.ScoreBoard;
import fr.weefle.waze.nms.Tablist;
import fr.weefle.waze.nms.Title;
import fr.weefle.waze.old.ActionBarOld;
import fr.weefle.waze.old.AutoRespawnOld;
import fr.weefle.waze.old.BossBarOld;
import fr.weefle.waze.old.ParticleOld;
import fr.weefle.waze.old.WazeEffectBossBarCreateOld;
import fr.weefle.waze.old.WazeEffectBossBarRemoveOld;
import fr.weefle.waze.old.WazeEffectBossBarTimerOld;
import fr.weefle.waze.skwrapper.SkWrapperReceiver;
import fr.weefle.waze.skwrapper.SkWrapperSender;
import fr.weefle.waze.skwrapper.WazeEffectCreateServer;
import fr.weefle.waze.skwrapper.WazeEffectStartServer;
import fr.weefle.waze.skwrapper.WazeEffectStopServer;

public class NMS {
	
	private Waze main;
	public NMS(Waze main) {
		this.main = main;
	}
	
	private static NMS instance;
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
	private Bot bot;
	
	public boolean isSet() {
		instance = this;
		String version;

	try {

		version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

	} catch (ArrayIndexOutOfBoundsException exception) {
		return false;
	}

	Bukkit.getLogger().info("Your server is running version " + version);
	
	scoreboard = new ScoreBoard();
	ping = new Ping();
	nametag = new Nametag();
	tablist = new Tablist();
	
	if (version.equals("v1_8_R3") || version.equals("v1_8_R2") || version.equals("v1_8_R1")) {
    	particle = new ParticleOld();
    	title = new Title();
		autorespawn = new AutoRespawnOld(main);
		bossbar = new BossBarOld();
		actionbar = new ActionBarOld();
    }else if (version.equals("v1_7_R4") || version.equals("v1_7_R3") || version.equals("v1_7_R2") || version.equals("v1_7_R1")){
    	particle = new ParticleOld();
		autorespawn = new AutoRespawnOld(main);
		bossbar = new BossBarOld();
    }else if(version.equals("v1_9_R1") || version.equals("v1_9_R2")) {
    	title = new Title();
    	particle = new ParticleNew();
    	autorespawn = new AutoRespawnNew();
		bossbar = new BossBarNew(main);
		actionbar = new ActionBarOld();
}else {
	Skript.registerEvent("Advancement Done Event", SimpleEvent.class, PlayerAdvancementDoneEvent.class, "[waze] advancement [(done|obtained|won)]");
	 EventValues.registerEventValue(PlayerAdvancementDoneEvent.class, Player.class, new Getter<Player, PlayerAdvancementDoneEvent>() {
           @Override
           public Player get(PlayerAdvancementDoneEvent playerAdvancementDoneEvent) {
               return playerAdvancementDoneEvent.getPlayer();
           }
       }, 0);
       EventValues.registerEventValue(PlayerAdvancementDoneEvent.class, Advancement.class, new Getter<Advancement, PlayerAdvancementDoneEvent>() {
           @Override
           public Advancement get(PlayerAdvancementDoneEvent playerAdvancementDoneEvent) {
               return playerAdvancementDoneEvent.getAdvancement();
           }
       }, 0);
       Classes.registerClass(new ClassInfo<Advancement>(Advancement.class, "advancement").user("advancement(s)?").name("Advancement").parser(new Parser<Advancement>() {

   		@Override
   		public String getVariableNamePattern() {
   			return ".+";
   		}

   		@Override
   		@Nullable
   		public Advancement parse(String arg0, ParseContext arg1) {
   			return null;
   		}

   		@Override
   		public String toString(Advancement arg0, int arg1) {
   			return arg0.toString();
   		}

   		@Override
   		public String toVariableNameString(Advancement arg0) {
   			return arg0.toString();
   		}
   	   
   	}));
	title = new Title();
	particle = new ParticleNew();
	autorespawn = new AutoRespawnNew();
	bossbar = new BossBarNew(main);
	actionbar = new ActionBarNew();
}
	PlayerJumpEvent.register(main);
	PlayerSwimEvent.register(main);
	Bukkit.getServer().getPluginManager().registerEvents(new SkWrapperSender(), main);
	Bukkit.getServer().getPluginManager().registerEvents(new SkWrapperReceiver(), main);
	Bukkit.getServer().getPluginManager().registerEvents(new UpdaterListener(), main);
	Skript.registerAddon(main);
    Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %players% (for|to) %integer% second[s]");
	Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %players%");
	Skript.registerEffect(WazeEffectBungee.class, "[waze] (send|teleport) %players% to [bungee[cord]] server %string%");
	Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %players%['s] ping", "[waze] ping of %players%");
	Skript.registerExpression(WazeExpressionBossBar.class, String.class, ExpressionType.PROPERTY, "[waze] %players%['s] [boss]bar [list]", "[waze] [boss]bar [list] of %players%");
	//Skript.registerExpression(WazeExpressionServersList.class, String.class, ExpressionType.PROPERTY, "[waze] [skwrapper] servers list from [template] %string%", "[waze] list of [skwrapper] servers from [template] %string%", "[waze] [skwrapper] servers list from %string%['s] [template]", "[waze] list of [skwrapper] servers from %string%['s] [template]");
	Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
	Skript.registerEffect(WazeEffectClearRecipes.class, "[waze] (remove|clear|delete) [all] [craft[ing]] recipe[s]");
    Skript.registerEffect(WazeEffectScoreBoard.class, "[waze] (create|make) score[board] %string% of type %string% to [display]slot %string% (with|and) score %string% (at|for) line %integer% (to|for) %players%");
	Skript.registerEffect(WazeEffectRemoveScoreBoard.class, "[waze] (clear|remove) score[board] %string% (of|for) %players%");
	/*Skript.registerEffect(WazeEffectChangeScore.class, "[waze] (change|modify) score[board] (at|for) [display]slot %string% to [score] %string% (at|for) line %integer% (for|to) %players%");
	Skript.registerEffect(WazeEffectRemoveScore.class, "[waze] (remove|clear) score[board] score named %string% (for|to) %players%");*/
	Skript.registerEffect(WazeEffectTablist.class, "[waze] (set|show) tab[list] (with|from) [head[er]] %string% (and|with) [foot[er]] %string% (to|for) %players%");
	Skript.registerEffect(WazeEffectNametag.class, "[waze] (set|show) name[tag] %string% (to|for) %players%");
	Skript.registerEffect(WazeEffectAutoRespawn.class, "[waze] [auto]respawn %players%");
	Skript.registerEffect(WazeEffectParticles.class, "[waze] (spawn|create|summon) [a number of] %integer% [of] %string%['s] particle[s] (to|for) %players% (at|from) %locations% (and|with) offset %float%, %float%, %float% (and|with|at) speed %float%");
	Skript.registerEvent("Jump Event", SimpleEvent.class, PlayerJumpEvent.class, "[waze] jump[ing]");
	Skript.registerEvent("Swim Event", SimpleEvent.class, PlayerSwimEvent.class, "[waze] swim[ing]");
	Skript.registerEvent("Horse Jump Event", SimpleEvent.class, HorseJumpEvent.class, "[waze] horse jump[ing]");
    EventValues.registerEventValue(PlayerJumpEvent.class, Player.class, new Getter<Player, PlayerJumpEvent>() {
        @Override
        public Player get(PlayerJumpEvent playerJumpEvent) {
            return playerJumpEvent.getPlayer();
        }
    }, 0);
    EventValues.registerEventValue(PlayerSwimEvent.class, Player.class, new Getter<Player, PlayerSwimEvent>() {
        @Override
        public Player get(PlayerSwimEvent playerSwimEvent) {
            return playerSwimEvent.getPlayer();
        }
    }, 0);
    if(Bukkit.getServer().getPluginManager().getPlugin("Socket4MC") != null) {
    	Skript.registerEffect(WazeEffectCreateServer.class, "[waze] (add|create) [[a] new] [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStartServer.class, "[waze] (start|begin) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStopServer.class, "[waze] (stop|end) [skwrapper] server named %string% (from|with) template %string%");
		main.getLogger().info("Socket4MC setup was successful!");
	}else {
		main.getLogger().severe("Failed to setup Socket4MC!");
	}
    if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null && Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises") != null) {
    	Skript.registerEffect(WazeEffectDisguisePlayer.class, "[waze] (disguise|transform|morph) player %player% as player %string%");
		Skript.registerEffect(WazeEffectDisguiseMob.class, "[waze] (disguise|transform|morph) player %player% as mob %string%");
		Skript.registerEffect(WazeEffectDisguiseMisc.class, "[waze] (disguise|transform|morph) player %player% as misc %string%");
		main.getLogger().info("ProtocolLib and LibsDisguises setup was successful!");
	}else {
		main.getLogger().severe("Failed to setup ProtocolLib and LibsDisguises! do you have both installed?");
	}
	if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null && Bukkit.getServer().getPluginManager().getPlugin("Builder") != null) {
		Skript.registerEffect(WazeEffectBuilder.class, "[waze] (make|let) citizen[s] with id %number% build schem[atic] %string% at [location] %location% (with|at) speed %number% (for|to) %player%");
		main.getLogger().info("Citizens and Builder setup was successful!");
	}else {
		main.getLogger().severe("Failed to setup Citizens and Builder! do you have both installed?");
	}
	if(Bukkit.getServer().getPluginManager().getPlugin("BossBarAPI") != null) {
		Skript.registerEffect(WazeEffectBossBarCreateOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarTimerOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (for|and) %integer% second[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarRemoveOld.class, "[waze] 1.8 (remove|delete|clear) [boss]bar (of|for) %players%");
		main.getLogger().info("BossBarAPI setup was successful! 1.8 BossBar activated!");
	}else {
		Skript.registerEffect(WazeEffectBossBarCreate.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarTimer.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (for|and) %integer% second[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarRemove.class, "[waze] (remove|delete|clear) [boss]bar with id %string% (of|for) %players%");
		main.getLogger().severe("Failed to setup BossBarAPI! activating 1.9 Weefle BossBar API!");
	}
	if(Bukkit.getServer().getPluginManager().getPlugin("Discord-ProgramBot-API") != null) {
		discord = new DiscordRegister(main);
		bot = new Bot("NDYxNTk3MzYyODcyMTIzMzkz.DhVocQ.px7FnBq7Z8XJw9vW97H0hriGenI", "[Wazea]");
		discord.initialiseBot(bot);
		//bot.addCommand(new PingCommand());
		main.getLogger().info("Discord-ProgramBot-API setup was successful!");
	}else {
		main.getLogger().severe("Failed to setup Discord-ProgramBot-API!");
	}
	return true;
}
	
	public ActionBarAPI getActionbar() {
        return actionbar;
    }
    public Title getTitle() {
        return title;
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

public static NMS getInstance() {
	return instance;
}
}

