package fr.weefle.waze.nms;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.weefle.waze.Waze;

public class HologramAPI {
	
	private HashMap<Player, Map<String, Hologram>> holos = new HashMap<>();
	private HashMap<String, Hologram> holo = new HashMap<>();

	public void createHologram(Player p, String message, Location loc, String id) {
		if(!holos.containsKey(p)) {
		holo.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
		holos.put(p, holo);
		holos.get(p).get(id).appendTextLine(message);
		}
	}
	
	public void teleportHologram(Player p, String id, Location loc) {
		if(holos.containsKey(p)) {
		holos.get(p).get(id).teleport(loc);
	}}
	
	public void setLineHologram(Player p, String id, int line, String msg) {
		if(holos.containsKey(p)) {
			if(holos.get(p).containsKey(id)) {
		holos.get(p).get(id).insertTextLine(line, msg);
	}}}
	
	public void removeLinesHologram(Player p, String id) {
		if(holos.containsKey(p)) {
		holos.get(p).get(id).clearLines();
	}}

	public void removeHologram(Player p, String id) {
		if(holos.containsKey(p)) {
			holos.get(p).get(id).delete();
			holos.get(p).remove(id);
}}

	public String getHolograms(Player p) {
		if(holos.containsKey(p)) {
				return holos.get(p).values().toString();
		}
		return null;
		
	}

}
