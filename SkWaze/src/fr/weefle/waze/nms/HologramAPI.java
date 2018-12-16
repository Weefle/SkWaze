package fr.weefle.waze.nms;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.weefle.waze.Waze;

public class HologramAPI {

	private HashMap<String, Hologram> holo = new HashMap<>();
	
	public void createHologram(String message, Location loc, String id) {
		if(holo.isEmpty()) {
			loc.setY(loc.getY()+2.5);
		holo.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
		holo.get(id).appendTextLine(message);
		}else {
			holo.get(id).appendTextLine(message);
			loc.setY(loc.getY()+2.5);
			holo.get(id).teleport(loc);
		}
		
	}
	
	public void teleportHologram(String id, Location loc) {
		if(!holo.isEmpty()) {
			if(holo.containsKey(id)) {
				loc.setY(loc.getY()+2.5);
		holo.get(id).teleport(loc);

}}}
	
	public void addLineHologram(String id, String msg, int index) {
		if(!holo.isEmpty()) {
			if(holo.containsKey(id)) {
				if(index!=0) {
		holo.get(id).getLine(index-1).getParent().insertTextLine(index-1, msg);
		holo.get(id).removeLine(index);
				}
		}}}
	
	public void addItemLineHologram(String id, ItemStack itemstack, int index) {
		if(!holo.isEmpty()) {
			if(holo.containsKey(id)) {
				if(index!=0) {
		holo.get(id).getLine(index-1).getParent().insertItemLine(index-1, itemstack);
		holo.get(id).removeLine(index);
				}

	}}}
	
	public void removeLineHologram(String id, int index) {
		if(!holo.isEmpty()) {
			if(holo.containsKey(id)) {
				if(index!=0) {
			holo.get(id).removeLine(index-1);
			}
	
	}}}

	public void removeHologram(String id) {
		if(!holo.isEmpty()) {
			if(holo.containsKey(id)) {
				holo.get(id).clearLines();
			holo.get(id).delete();
			holo.remove(id);
			holo.remove(id);
			holo.clear();
}}}

	public String[] getHolograms() {
		if(!holo.isEmpty()) {
			return holo.keySet().toArray(new String[holo.keySet().size()]);
	}else {

		return null;
		}

}}
