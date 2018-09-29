package fr.weefle.waze.nms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.weefle.waze.Waze;

public class HologramAPI {
	
	private HashMap<Player, Map<String, Hologram>> holos = new HashMap<>();
	private HashMap<String, Hologram> holostatic = new HashMap<>();
	private HashMap<String, Hologram> holofollow = new HashMap<>();
//	private ArrayList<String> holosf = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();

	public void createHologram(Player p, String message, Location loc, String id, boolean bool) {
		if(holos.containsKey(p)) {
		if(holos.get(p).containsKey(id)) {
			//holos.get(p).get(id).appendTextLine(message);
		}else{
			if(bool==true){
				players.add(p);
				//holosf.add(id);
				holofollow.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
		holos.put(p, holofollow);
		holos.get(p).get(id).appendTextLine(message);
			}
			else {holostatic.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
		holos.put(p, holostatic);
		holos.get(p).get(id).appendTextLine(message);
		players.add(p);
		}
		}}else {
			holostatic.put(id, HologramsAPI.createHologram(Waze.getInstance(), loc));
			holos.put(p, holostatic);
			holos.get(p).get(id).appendTextLine(message);
			players.add(p);
		}
	}

	public void removeHologram(Player p, String id) {
		if(holos.containsKey(p)) {
		if(holos.get(p).containsKey(id)) {
		//	holosf.remove(id);
			holos.get(p).get(id).delete();
			holos.get(p).remove(id);
			players.remove(p);

}}}

	public String getHologram(Player p) {
		if(holos.containsKey(p)) {
				return holos.get(p).values().toString();
		}
		return null;
		
	}
	
public void holoTrack() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(holos != null) {
						if(players != null) {
						for(int p=0;p<players.size();p++) {
						//	NMS.getInstance().getHolograms().players.get(p).sendMessage("oui");
						//	NMS.getInstance().getHolograms().players.get(p).sendMessage(NMS.getInstance().getHolograms().holosf.toString());
							//if(holosf != null) {
								//for(String h : holosf) {
									//NMS.getInstance().getHolograms().players.get(p).sendMessage("yeah");
									//NMS.getInstance().getHolograms().players.get(p).sendMessage(NMS.getInstance().getHolograms().holofollow.get(h).toString());
									//holofollow.get(h).teleport(players.get(p).getLocation());
							holofollow.get(holofollow.entrySet().iterator().next().getKey()).teleport(players.get(p).getLocation());
								//}
							//}
							}}
			}
			}
			
		}.runTaskTimer(Waze.getInstance(), 1L, 1L);
		
	}

}
