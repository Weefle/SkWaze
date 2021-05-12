package fr.weefle.waze.utils;

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
import fr.weefle.waze.conditions.WazeConditionHologram;
import fr.weefle.waze.effects.*;
import fr.weefle.waze.events.PlayerJumpEvent;
import fr.weefle.waze.events.PlayerSwimEvent;
import fr.weefle.waze.expressions.WazeExpressionBossBar;
import fr.weefle.waze.expressions.WazeExpressionDisguise;
import fr.weefle.waze.expressions.WazeExpressionHologram;
import fr.weefle.waze.expressions.WazeExpressionPing;
import fr.weefle.waze.legacy.*;
import fr.weefle.waze.nms.*;
import fr.weefle.waze.skwrapper.conditions.WazeConditionIsServerOnline;
import fr.weefle.waze.skwrapper.effects.*;
import fr.weefle.waze.skwrapper.expressions.WazeExpressionBungeeOnlineCountGlobal;
import fr.weefle.waze.skwrapper.expressions.WazeExpressionBungeeServerList;
import fr.weefle.waze.skwrapper.expressions.WazeExpressionNetworkVariable;
import fr.weefle.waze.skwrapper.expressions.WazeExpressionPlayerServer;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import javax.annotation.Nullable;

public class NMS {

	private static NMS instance;
	private ActionBarAPI actionbar;
	private Title title;
	private BossBarAPI bossbar;
	private Ping ping;
	private Tablist tablist;
	private ParticleAPI particle;
	private Nametag nametag;
	private SideBar sidebar;
	private AutoRespawnAPI autorespawn;
	private HologramAPI holograms;
	private String version;
	public static int ver;

	public boolean isSet() {
		instance = this;

		try {

			version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

		} catch (ArrayIndexOutOfBoundsException exception) {
			return false;
		}

		Bukkit.getLogger().info("Your server is running version " + version);

		ver = Integer.parseInt(version.replace("_",  ",").split(",")[1]);

		ping = new Ping();
		nametag = new Nametag();
		tablist = new Tablist();

		if(ver < 10){
			if(ver > 7){
				title = new Title();
				actionbar = new ActionBarOld();
			}
			if(ver == 9){
				actionbar = new ActionBarOld();
			}else{
				actionbar = new ActionBarNew();
			}
			particle = new ParticleOld();
			autorespawn = new AutoRespawnOld();
			bossbar = new BossBarOld();
		} else {
				title = new Title();
				particle = new ParticleNew();
				autorespawn = new AutoRespawnNew();
				bossbar = new BossBarNew();
				actionbar = new ActionBarNew();

			if(ver > 12) {
				Skript.registerEvent("Toggle Swim Event", SimpleEvent.class, EntityToggleSwimEvent.class, "[waze] toggle swim[ing]");
				EventValues.registerEventValue(EntityToggleSwimEvent.class, Player.class, new Getter<Player, EntityToggleSwimEvent>() {
					public Player get(EntityToggleSwimEvent entityToggleSwimEvent) {
						return (Player) entityToggleSwimEvent.getEntity();
					}
				}, 0);

			}

			if(ver > 11) {
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
				Classes.registerClass(new ClassInfo<>(Advancement.class, "advancement").user("advancement(s)?").name("Advancement").parser(new Parser<Advancement>() {

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
						return arg0.getKey().getKey();
					}

					@Override
					public String toVariableNameString(Advancement arg0) {
						return arg0.toString();
					}

				}));
			}

			if(ver > 10) {
				EventValues.registerEventValue(HorseJumpEvent.class, AbstractHorse.class, new Getter<AbstractHorse, HorseJumpEvent>() {
					public AbstractHorse get(HorseJumpEvent horseJumpEvent) {
						return horseJumpEvent.getEntity();
					}
				}, 0);
			}

		}

		PlayerJumpEvent.register(Waze.getInstance());
		PlayerSwimEvent.register(Waze.getInstance());
		//PlayerJoinServerEvent.register(Waze.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(new UpdaterListener(), Waze.getInstance());
		Skript.registerAddon(Waze.getInstance());
		Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %players% (for|to) %integer% second[s]");
		Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %players%");
		Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %players%['s] ping [list]", "[waze] ping [list] of %players%");
		Skript.registerExpression(WazeExpressionBossBar.class, String.class, ExpressionType.PROPERTY, "[waze] %players%['s] [boss]bar [list]", "[waze] [boss]bar [list] of %players%");
		Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
		Skript.registerEffect(WazeEffectClearRecipes.class, "[waze] (remove|clear|delete) [all] [craft[ing]] recipe[s]");
		Skript.registerEffect(WazeEffectTablist.class, "[waze] (set|show) tab[list] (with|from) [head[er]] %string% (and|with) [foot[er]] %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectNametag.class, "[waze] (set|show) name[tag] %string% (to|for) %players%");
		Skript.registerEffect(WazeEffectAutoRespawn.class, "[waze] [auto]respawn %players%");
		Skript.registerEffect(WazeEffectParticles.class, "[waze] (spawn|create|summon) [a number of] %integer% [of] %string%['s] particle[s] (to|for) %players% (at|from) %locations% (and|with) offset %float%, %float%, %float% (and|with|at) speed %float%");
		Skript.registerEvent("Jump Event", SimpleEvent.class, PlayerJumpEvent.class, "[waze] jump[ing]");
		//Skript.registerEvent("Join Server Event", SimpleEvent.class, PlayerJoinServerEvent.class, "[skwrapper] player join[ing]");
		Skript.registerEvent("Swim Event", SimpleEvent.class, PlayerSwimEvent.class, "[waze] swim[ing]");
		Skript.registerEvent("Horse Jump Event", SimpleEvent.class, HorseJumpEvent.class, "[waze] horse jump[ing]");
		/*EventValues.registerEventValue(PlayerJoinServerEvent.class, Player.class, new Getter<Player, PlayerJoinServerEvent>() {
			public Player get(PlayerJoinServerEvent playerJoinServerEvent) {
				return playerJoinServerEvent.getPlayer();
			}
		},  0);
		EventValues.registerEventValue(PlayerJoinServerEvent.class, String.class, new Getter<String, PlayerJoinServerEvent>() {
			public String get(PlayerJoinServerEvent playerJoinServerEvent) {
				return playerJoinServerEvent.getServer();
			}
		},  0);*/
		EventValues.registerEventValue(PlayerSwimEvent.class, Player.class, new Getter<Player, PlayerSwimEvent>() {
			public Player get(PlayerSwimEvent playerSwimEvent) {
				return playerSwimEvent.getPlayer();
			}
		},  0);

		sidebar = new SideBar();
		Skript.registerEffect(WazeEffectSetSideBar.class, "[waze] (change|set) sidebar %string% (with|and) score %string% (at|for) line %integer% (to|for) %players%");
		Skript.registerEffect(WazeEffectRemoveSideBar.class, "[waze] (clear|remove|delete) sidebar (of|for) %players%");
		Skript.registerEffect(WazeEffectRemoveLineSideBar.class, "[waze] (clear|remove|delete) line %integer% (of|from) sidebar (of|for) %players%");


		Skript.registerExpression(WazeExpressionBungeeOnlineCountGlobal.class, Integer.class, ExpressionType.PROPERTY, "[waze] [number of] online player[s] on bungee[cord]");
		Skript.registerEffect(WazeEffectSendAllMessage.class, "[waze] send network message %string% to proxy players");
		Skript.registerEffect(WazeEffectSendMessage.class, "[waze] send network message %string% to %players%");
		Skript.registerEffect(WazeEffectSendAllTitle.class, "[waze] send network title %string% [with] subtitle %string% (to|for) proxy players (for|to) %integer% second[s]");
		Skript.registerEffect(WazeEffectSendTitle.class, "[waze] send network title %string% [with] subtitle %string% (to|for) %players% (for|to) %integer% second[s]");
		Skript.registerEffect(WazeEffectRunProxyCommand.class, "[waze] run proxy command %string%");
		Skript.registerEffect(WazeEffectSendAllActionBar.class, "[waze] send network actionbar %string% to proxy players");
		Skript.registerEffect(WazeEffectSendActionBar.class, "[waze] send network actionbar %string% to %players%");
		Skript.registerEffect(WazeEffectSendAllKick.class, "[waze] kick all [player[s]] with [message] %string%");
		Skript.registerEffect(WazeEffectSendKick.class, "[waze] kick [player] %players% with [message] %string%");
		Skript.registerEffect(WazeEffectBungeeConnect.class, "[waze] (send|teleport) %players% to [bungee[cord]] server %string%");
		Skript.registerEffect(WazeEffectRefreshTemplates.class, "[waze] refresh [all] [skwrapper] server templates");
		Skript.registerEffect(WazeEffectCreateServer.class, "[waze] (add|create) [[a] new] [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectDeleteServer.class, "[waze] (remove|delete) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStartServer.class, "[waze] (start|begin) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStartAllServers.class, "[waze] (start|begin) all [skwrapper] servers");
		Skript.registerEffect(WazeEffectStartAllServersFrom.class, "[waze] (start|begin) all [skwrapper] servers from template %string%");
		Skript.registerEffect(WazeEffectStopAllServers.class, "[waze] (stop|end) all [skwrapper] servers");
		Skript.registerEffect(WazeEffectStopAllServersFrom.class, "[waze] (stop|end) all [skwrapper] servers from template %string%");
		Skript.registerEffect(WazeEffectStopServer.class, "[waze] (stop|end) [skwrapper] server named %string% (from|with) template %string%");
		Skript.registerEffect(WazeEffectStopProxy.class, "[waze] (stop|end|shut[ ]down) [skwrapper] proxy [server]");
		Skript.registerExpression(WazeExpressionBungeeServerList.class, String.class, ExpressionType.PROPERTY, "[waze] [bungee[cord]] server[s] list", "[waze] [bungee[cord]] list of server[s]");
		Skript.registerExpression(WazeExpressionPlayerServer.class, String.class, ExpressionType.PROPERTY, "[waze] [bungee[cord]] %player% server", "[waze] [bungee[cord]] server of %player%");
		Skript.registerExpression(WazeExpressionNetworkVariable.class, Object.class, ExpressionType.COMBINED, "[waze] (global|network) variable [(from|of)] %objects%");
		Skript.registerCondition(WazeConditionIsServerOnline.class, "[waze] server %string% is online");
		Waze.getInstance().getLogger().info("BungeeCord setup was successful, your data will be sent across the network!");


		if(Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
			holograms = new HologramAPI();
			Skript.registerCondition(WazeConditionHologram.class, "[waze] holo id %string% exist[s]");
			Skript.registerEffect(WazeEffectCreateHologram.class, "[waze] (create|spawn) [[a] new] hologram display[ing] %string% (at|from) %locations% (and|with) id %string%");
			Skript.registerEffect(WazeEffectRemoveHologram.class, "[waze] (delete|remove|clear) hologram with id %string%");
			Skript.registerEffect(WazeEffectTeleportHologram.class, "[waze] (teleport|move) hologram with id %string% (to|at) %locations%");
			Skript.registerEffect(WazeEffectSetLineHologram.class, "[waze] (set|change) line %integer% (at|from) hologram with id %string% (to|with) %string%");
			Skript.registerEffect(WazeEffectSetItemLineHologram.class, "[waze] (set|change) line %integer% (at|from) hologram with id %string% (to|with) item %string%");
			Skript.registerEffect(WazeEffectAddLineHologram.class, "[waze] (add|append) [a] new line (at|from) hologram with id %string% (to|with) %string%");
			Skript.registerEffect(WazeEffectAddItemLineHologram.class, "[waze] (add|append) [a] new line (at|from) hologram with id %string% (to|with) item %string%");
			Skript.registerEffect(WazeEffectRemoveLineHologram.class, "[waze] (clear|remove|delete) line %integer% (at|from) hologram with id %string%");
			Skript.registerExpression(WazeExpressionHologram.class, String.class, ExpressionType.PROPERTY, "[waze] hologram['s] list", "[waze] list of hologram");
			Waze.getInstance().getLogger().info("HolographicDisplays setup was successful, you can now create holograms!");
		}else {
			Waze.getInstance().getLogger().severe("Failed to setup HolographicDisplays, you need it installed to use holograms features!");
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null && Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises") != null) {
			Skript.registerEffect(WazeEffectDisguisePlayer.class, "[waze] (disguise|transform|morph) %players% (as|in[to]) player %string% view[itself] %boolean%");
			Skript.registerEffect(WazeEffectDisguiseMob.class, "[waze] (disguise|transform|morph) %players% (as|in[to]) mob %string% view[itself] %boolean%");
			Skript.registerEffect(WazeEffectDisguiseMisc.class, "[waze] (disguise|transform|morph) %players% (as|in[to]) misc %string% view[itself] %boolean%");
			Skript.registerEffect(WazeEffectUnDisguise.class, "[waze] (undisguise|untransform|unmorph) %players%");
			Skript.registerExpression(WazeExpressionDisguise.class, String.class, ExpressionType.PROPERTY, "[waze] %players%['s] disguise [list]", "[waze] disguise [list] of %players%");
			Waze.getInstance().getLogger().info("ProtocolLib and LibsDisguises setup was successful!");
		}else {
			Waze.getInstance().getLogger().severe("Failed to setup ProtocolLib and LibsDisguises! do you have both installed?");
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null && Bukkit.getServer().getPluginManager().getPlugin("Builder") != null) {
			Skript.registerEffect(WazeEffectBuilder.class, "[waze] (make|let) citizen[s] with id %number% build schem[atic] %string% (from|at) %location% (with|at) speed %number% (for|to) %players%");
			Waze.getInstance().getLogger().info("Citizens and Builder setup was successful!");
		}else if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") != null && Bukkit.getServer().getPluginManager().getPlugin("Constructor") != null){
			Skript.registerEffect(WazeEffectConstructorSchematic.class, "[waze] (make|let) citizen[s] with id %number% build schem[atic] %string% (from|at) %location% (with|at) speed %number% (for|to) %players%");
			Skript.registerEffect(WazeEffectConstructorStructure.class, "[waze] (make|let) citizen[s] with id %number% build (nbt|structure) %string% (from|at) %location% (with|at) speed %number% (for|to) %players%");
			Waze.getInstance().getLogger().info("Citizens and Constructor setup was successful!");
		}
		else {
			Waze.getInstance().getLogger().severe("Failed to setup Citizens and Builder/Constructor! do you have both installed?");
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("BossBarAPI") != null) {
			Skript.registerEffect(WazeEffectBossBarCreateOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarTimerOld.class, "[waze] 1.8 (create|send) [boss]bar %string% (with|at) %integer% percent[s] (for|and) %integer% second[s] (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarRemoveOld.class, "[waze] 1.8 (remove|delete|clear) [boss]bar (of|for) %players%");
			Waze.getInstance().getLogger().info("BossBarAPI setup was successful! 1.8 BossBar activated!");
		}else {
			Skript.registerEffect(WazeEffectBossBarCreate.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarTimer.class, "[waze] (create|send) [boss]bar %string% (with|at) %integer% percent[s] (and|with) color %string% (and|with) style %string% with id %string% (for|and) %integer% second[s] (to|for) %players%");
			Skript.registerEffect(WazeEffectBossBarRemove.class, "[waze] (remove|delete|clear) [boss]bar with id %string% (of|for) %players%");
			Waze.getInstance().getLogger().severe("Failed to setup BossBarAPI! activating 1.9 Weefle BossBar API!");
		}

		//TODO discord support
		//new MessageListener();

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
	public SideBar getSideBar(){
		return sidebar;
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

	public HologramAPI getHolograms(){
		return holograms;
	}

	public static NMS getInstance() {
		return instance;
	}
}
