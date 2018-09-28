package fr.weefle.waze.nms;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.weefle.waze.Waze;

public class HologramAPI {

	public ArrayList<Player> players = new ArrayList<>();
	public HashMap<Player, ArrayList<Hologram>> holos = new HashMap<>();
	public ArrayList<Hologram> holostatic = new ArrayList<>();
	public ArrayList<Hologram> holofollow = new ArrayList<>();
	
	public void createHolo(Location loc, String msg, Player player, boolean bool) {
		if(bool == true) {
			Hologram holo = HologramsAPI.createHologram(Waze.getInstance(), loc);
			holo.appendTextLine(msg);
			holofollow.add(holo);
			players.add(player);
			holos.put(player, holofollow);
		}else {
			Hologram holo = HologramsAPI.createHologram(Waze.getInstance(), loc);
			holo.appendTextLine(msg);
			holostatic.add(holo);
			players.add(player);
			holos.put(player, holostatic);
		}
	}
	
	public void removeHolo(Player player) {
		for(Hologram h : holos.get(player))
			holos.get(player).remove(h);
		players.remove(player);
	}

}
