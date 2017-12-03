package fr.weefle.waze;

import fr.weefle.waze.effects.*;
import fr.weefle.waze.nms.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import fr.weefle.waze.events.PlayerJumpEvent;
import fr.weefle.waze.expressions.WazeExpressionPing;

public class Waze extends JavaPlugin {
	
	private static Waze instance;
	private ActionBar actionbar;
	private Title title;
	private BossBar bossbar;
	private Ping ping;
	private ScoreBoard scoreboard;
	
	@Override
	public void onEnable() {
		if (setupNMS()) {

			getLogger().info("NMS setup was successful!");
			getLogger().info("The plugin setup process is complete!");

		} else {

			getLogger().severe("Failed to setup NMS!");
			getLogger().severe("Your server version is not compatible with this plugin!");

			Bukkit.getPluginManager().disablePlugin(this);
		}
		instance = this;
		PlayerJumpEvent.register(this);
		Skript.registerAddon(this);
        Skript.registerEffect(WazeEffectTitle.class, "[waze] (send|create) title %string% with [sub[title]] %string% (to|for) %player% (for|to) %integer% tick[s]");
		Skript.registerEffect(WazeEffectActionBar.class, "[waze] (send|create) action[bar] %string% (to|for) %player%");
		Skript.registerEffect(WazeEffectBungee.class, "[waze] (send|teleport) %player% to [bungee[cord]] server %string%");
		Skript.registerExpression(WazeExpressionPing.class, Integer.class, ExpressionType.PROPERTY, "[waze] %player%['s] ping", "[waze] ping of %player%");
		Skript.registerEffect(WazeEffectRecipe.class, "[waze] (create|register) [new] recipe[s] [for] %itemtype% with %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%");
		Skript.registerEffect(WazeEffectClearRecipes.class, "[waze] (remove|clear|delete) [all] [craft[ing]] recipe[s]");
		Skript.registerEffect(WazeEffectBossBarCreate.class, "[waze] (create|send) [boss]bar %string% (with|at) %double% percent[s] (and|with) color %string% (to|for) %player%");
		Skript.registerEffect(WazeEffectBossBarTime.class, "[waze] (create|send) [boss]bar %string% (with|at) %double% percent[s] (and|with) color %string% (for|and) %integer% tick[s] (to|for) %player%");
		Skript.registerEffect(WazeEffectBossBarRemove.class, "[waze] (remove|delete|clear) [boss]bar (of|for) %player%");
        Skript.registerEffect(WazeEffectScoreBoard.class, "[waze] (create|make) scoreboard %string% of type %string% to [display]slot %string% (with|and) score %string% (at|for) line %integer% (to|for) %player%");
		Skript.registerEffect(WazeEffectRemoveScoreBoard.class, "[waze] (clear|remove) scoreboard %string% (of|for) %player%");
        Skript.registerEffect(WazeEffectBossBarRemoveAll.class, "[waze] (remove|delete|clear) all [boss]bar");
		Skript.registerEvent("Jump Event", SimpleEvent.class, PlayerJumpEvent.class, "[waze] jump[ing]");
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

		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return false;
		}

		getLogger().info("Your server is running version " + version);

		if (version.equals("v1_12_R1")) {
			actionbar = new ActionBar_1_12_R1();
            title = new Title_1_12_R1();
            bossbar = new BossBar_1_9_R1(this);
            ping = new Ping_1_12_R1();
			scoreboard = new ScoreBoard_1_9_R1();
		} else if (version.equals("v1_11_R1")) {
			actionbar = new Actionbar_1_11_R1();
			title = new Title_1_11_R1();
            bossbar = new BossBar_1_9_R1(this);
            ping = new Ping_1_11_R1();
            scoreboard = new ScoreBoard_1_9_R1();
		}
		return actionbar != null;
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
    public BossBar getBossBar(){
        return bossbar;
    }
    public Ping getPing(){
        return ping;
    }
    public ScoreBoard getScoreBoard(){
	    return scoreboard;
    }

}