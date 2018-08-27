package fr.weefle.waze.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.tjplaysnow.discord.object.Bot;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
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
import fr.weefle.waze.old.WazeEffectBossBarCreateOld;
import fr.weefle.waze.old.WazeEffectBossBarRemoveOld;
import fr.weefle.waze.old.WazeEffectBossBarTimerOld;
import fr.weefle.waze.skwrapper.SkWrapperReceiver;
import fr.weefle.waze.skwrapper.SkWrapperSender;
import fr.weefle.waze.skwrapper.WazeEffectCreateServer;
import fr.weefle.waze.skwrapper.WazeEffectStartServer;
import fr.weefle.waze.skwrapper.WazeEffectStopServer;

public class Register {
	
	private DiscordRegister discord;
	private Bot bot;
	private Waze main;
	public Register(Waze main) {
		this.setMain(main);
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
		Skript.registerEffect(WazeEffectBossBarCreate.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarTimer.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (for|and) %integer% second[s] (to|for) %players%");
		Skript.registerEffect(WazeEffectBossBarRemove.class, "[waze] (remove|delete|clear) [boss]bar with id %string% (of|for) %players%");
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
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("Socket4MC")) {
        	Skript.registerEffect(WazeEffectCreateServer.class, "[waze] (add|create) [[a] new] [skwrapper] server named %string% (from|with) template %string%");
    		Skript.registerEffect(WazeEffectStartServer.class, "[waze] (start|begin) [skwrapper] server named %string% (from|with) template %string%");
    		Skript.registerEffect(WazeEffectStopServer.class, "[waze] (stop|end) [skwrapper] server named %string% (from|with) template %string%");
			Bukkit.getLogger().info("Socket4MC setup was successful!");
		}else {
			Bukkit.getLogger().severe("Failed to setup Socket4MC!");
		}
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("Citizens") && Bukkit.getServer().getPluginManager().isPluginEnabled("Builder")) {
			Skript.registerEffect(WazeEffectBuilder.class, "[waze] (make|let) citizen with id %number% build schem[atic] %string% at [location] %location% (with|at) speed %number% (for|to) %player%");
			Bukkit.getLogger().info("Citizens and Builder setup was successful!");
		}else {
			Bukkit.getLogger().severe("Failed to setup Citizens and Builder! do you have both installed?");
		}
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("BossBarAPI")) {
			Skript.registerEffect(WazeEffectBossBarCreateOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarTimerOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (for|and) %integer% second[s] (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarRemoveOld.class, "[waze] 1.8 (remove|delete|clear) [boss]bar (of|for) %players%");
			Bukkit.getLogger().info("BossBarAPI setup was successful!");
		}else {
			Bukkit.getLogger().severe("Failed to setup BossBarAPI!");
		}
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("Discord-ProgramBot-API")) {
			discord = new DiscordRegister(main);
			bot = new Bot("NDYxNTk3MzYyODcyMTIzMzkz.DhVocQ.px7FnBq7Z8XJw9vW97H0hriGenI", "[Wazea]");
			discord.initialiseBot(bot);
			//bot.addCommand(new PingCommand());
			Bukkit.getLogger().info("Discord-ProgramBot-API setup was successful!");
		}else {
			Bukkit.getLogger().severe("Failed to setup Discord-ProgramBot-API!");
		}
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
	}
	public Waze getMain() {
		return main;
	}
	public void setMain(Waze main) {
		this.main = main;
	}
	
}
