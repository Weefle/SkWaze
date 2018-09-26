package fr.weefle.waze.nms;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.weefle.waze.Waze;

public class HologramAPI {

	private HashMap<Player, ArrayList<Hologram>> holos = new HashMap<>();
	private ArrayList<Hologram> holo1 = new ArrayList<>();
	
	public void createHolo(Location loc, String msg, Player player) {
		Hologram holo = HologramsAPI.createHologram(Waze.getInstance(), loc);
		holo.appendTextLine(msg);
		holo1.add(holo);
		holos.put(player, holo1);
	}
	
	public void removeHolo(Player player) {
		for(Hologram h : holos.get(player))
		holos.get(player).remove(h);
	}

}
