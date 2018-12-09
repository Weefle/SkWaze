package fr.weefle.waze.effects;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.weefle.waze.utils.NMS;

public class WazeEffectAddItemLineHologram extends Effect {
	
	private Expression<Integer> line;
	private Expression<String> id;
	private Expression<String> item;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		line = (Expression<Integer>) arg0[0];
		id = (Expression<String>) arg0[1];
		item = (Expression<String>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "add line with item at hologram";
	}

	@Override
	protected void execute(Event arg0) {
		ItemStack i = new ItemStack(Material.valueOf(item.getSingle(arg0)));
	        		NMS.getInstance().getHolograms().addItemLineHologram(id.getSingle(arg0), i, line.getSingle(arg0));
		
		
		
	}

}
