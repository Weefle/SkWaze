package fr.weefle.waze.effects;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;
import fr.weefle.waze.Waze;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class WazeEffectParticles extends Effect {
	
	private Expression<Float> xoff;
	private Expression<Float> yoff;
	private Expression<Float> zoff;
	private Expression<Float> data;
	private Expression<String> particles;
	private Expression<Player> player;
	private Expression<Location> location;
	private Expression<Integer> number;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		number = (Expression<Integer>) arg0[0];
		particles = (Expression<String>) arg0[1];
		player = (Expression<Player>) arg0[2];
		location = (Expression<Location>) arg0[3];
		xoff = (Expression<Float>) arg0[4];
		yoff = (Expression<Float>) arg0[5];
		zoff = (Expression<Float>) arg0[6];
		data = (Expression<Float>) arg0[7];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send particles to player";
	}

	@Override
	protected void execute(Event arg0) {
		// TODO Auto-generated method stub
        try {
        	for(Player p : player.getAll(arg0)){
        		for(Location loc : location.getAll(arg0)){
            		//Waze.getInstance().getParticles().sendParticles(p, particles.getSingle(arg0), true, (float) (loc.getBlockX() + 0.5), loc.getBlockY(), (float) (loc.getBlockZ() + 0.5), xoff.getSingle(arg0), yoff.getSingle(arg0), zoff.getSingle(arg0), data.getSingle(arg0), number.getSingle(arg0), null);
        			Waze.getInstance().getParticles().sendParticles(p, particles.getSingle(arg0), loc, xoff.getSingle(arg0), yoff.getSingle(arg0), zoff.getSingle(arg0), data.getSingle(arg0), number.getSingle(arg0));
            	}
        	}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
