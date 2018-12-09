package fr.weefle.waze.nms;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.weefle.waze.Waze;

public class HologramAPI {

	private HashMap<Player, Map<String, Hologram>> holos = new HashMap<>();
	private HashMap<String, Hologram> holo = new HashMap<>();
	
	public void createHologram(String message, Location loc, String id, Player p) {
		if(!holos.containsKey(p)) {
			loc.setY(loc.getY()+2.5);
		holo.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
		holos.put(p, holo);
		holos.get(p).get(id).appendTextLine(message);
		}else {
			holos.get(p).get(id).appendTextLine(message);
			loc.setY(loc.getY()+2.5);
			holos.get(p).get(id).teleport(loc);
		}
		/*if(Waze.hologramManager.getActiveHolograms()==null) {
			//loc.setY(loc.getY()+2);
			Hologram hologram = new Hologram(id, loc);
		    Waze.hologramManager.addActiveHologram(hologram);
		    HologramLine line = new TextLine(Waze.hologramManager.getHologram(id), message);
		    Waze.hologramManager.getHologram(id).addLine(line);*/
		
	}
	
	public void teleportHologram(String id, Location loc, Player p) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
				loc.setY(loc.getY()+2.5);
		holos.get(p).get(id).teleport(loc);
		/*if(Waze.hologramManager.getHologram(id)==null) {
		//loc.setY(loc.getY()+2);
	Waze.hologramManager.getHologram(id).teleport(loc);
}*/
}}}
	
	public void addLineHologram(String id, String msg, int index, Player p) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
		//holos.get(p).get(id).insertTextLine(index, msg);
				if(index!=0) {
		holos.get(p).get(id).getLine(index-1).getParent().insertTextLine(index-1, msg);
		holos.get(p).get(id).removeLine(index);
				}
		/*if(Waze.hologramManager.getHologram(id)==null) {
				HologramLine line = new TextLine(Waze.hologramManager.getHologram(id), msg);
			    Waze.hologramManager.getHologram(id).addLine(line, index);
	}*/}}}
	
	public void addItemLineHologram(String id, ItemStack itemstack, int index, Player p) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
		//holos.get(p).get(id).insertItemLine(index, itemstack);
				if(index!=0) {
		holos.get(p).get(id).getLine(index-1).getParent().insertItemLine(index-1, itemstack);
		holos.get(p).get(id).removeLine(index);
				}
		/*if(Waze.hologramManager.getHologram(id)==null) {
	    HologramLine line = new ItemLine(Waze.hologramManager.getHologram(id), itemstack);
	    Waze.hologramManager.getHologram(id).addLine(line, index);
	}*/}}}
	
	public void removeLineHologram(String id, int index, Player p) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
		//holos.get(p).get(id).clearLines();
				if(index!=0) {
			holos.get(p).get(id).removeLine(index-1);
			}
		/*if(Waze.hologramManager.getHologram(id)==null) {
		Waze.hologramManager.getHologram(id).removeLine(Waze.hologramManager.getHologram(id).getLine(index));
		
	}*/}}}

	public void removeHologram(String id, Player p) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
				holos.get(p).get(id).clearLines();
			holos.get(p).get(id).delete();
			holos.get(p).remove(id);
			holos.remove(p);
			holos.clear();
	/*	if(Waze.hologramManager.getHologram(id)==null) {
		Waze.hologramManager.removeActiveHologram(Waze.hologramManager.getHologram(id));
}*/}}}

	public String[] getHolograms(Player p) {
		if(holos.containsKey(p)) {
			//return holos.get(p).values().toString();
			return holos.keySet().toArray(new String[holos.keySet().size()]);
	}else {
		/*if(Waze.hologramManager.getActiveHolograms()!=null) {
				return Waze.hologramManager.getActiveHolograms().values().toString();
		
	}*/
		return null;
		}

}}
