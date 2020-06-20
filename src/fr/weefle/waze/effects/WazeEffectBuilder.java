package fr.weefle.waze.effects;

import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.jrbudda.builder.BuilderTrait;
import net.jrbudda.builder.MCEditSchematicFormat;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectBuilder extends Effect {
	
	private Expression<Number> id;
	  private Expression<String> schematic;
	  private Expression<Location> loc;
	  private Expression<Number> speed;
	  private Expression<Player> player;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.id = (Expression<Number>) arg0[0];
	    this.schematic = (Expression<String>) arg0[1];
	    this.loc = (Expression<Location>) arg0[2];
	    this.speed = (Expression<Number>) arg0[3];
	    this.player = (Expression<Player>) arg0[4];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "build schematic/structure with citizen and builder for player";
	}

	@Override
	protected void execute(Event arg0) {
		  NPCRegistry registry = CitizensAPI.getNPCRegistry();
		    NPC npc = registry.getById(((Number)this.id.getSingle(arg0)).intValue());
		    npc.addTrait(BuilderTrait.class);
		    npc.teleport((Location)this.loc.getSingle(arg0), null);
		    if (this.speed != null) {
		      npc.getNavigator().getDefaultParameters().baseSpeed(((Number)this.speed.getSingle(arg0)).floatValue());
		    }
		    BuilderTrait bt = (BuilderTrait)npc.getTrait(BuilderTrait.class);
		    bt.oncancel = null;
		    bt.oncomplete = null;
		    bt.onStart = null;
		    bt.ContinueLoc = null;
		    bt.IgnoreAir = Boolean.valueOf(false);
		    bt.IgnoreLiquid = Boolean.valueOf(false);
		    bt.Excavate = Boolean.valueOf(false);
		    bt.GroupByLayer = Boolean.valueOf(true);
		    bt.BuildYLayers = Integer.valueOf(1);
		    bt.BuildPatternXY = BuilderTrait.BuildPatternsXZ.spiral;
		    File file = new File("plugins/Builder/schematics/");
		    try
		    {
		    	MCEditSchematicFormat mcEdit = new MCEditSchematicFormat();
		      bt.schematic = mcEdit.load(file, ((String)this.schematic.getSingle(arg0)).trim().replace("\"", ""));
		    }
		    catch (IOException exception)
		    {
		      exception.printStackTrace();
		    }
		    catch (Exception exception)
		    {
		      exception.printStackTrace();
		    }
		for(Player p : player.getAll(arg0)) {
			bt.TryBuild((CommandSender) p);
		}
    }

}
