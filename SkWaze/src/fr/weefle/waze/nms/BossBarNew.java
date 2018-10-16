package fr.weefle.waze.nms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import fr.weefle.waze.Waze;

public class BossBarNew implements BossBarAPI {
	
	private HashMap<Player, Map<String, BossBar>> bar = new HashMap<>();
	private HashMap<String, BossBar> boss = new HashMap<>();
	private ArrayList<Player> players = new ArrayList<>();
	private int task;

	@Override
	public void sendBossBar(Player p, String message, int percent, String color, String id, String type) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)) {
			bar.get(p).get(id).setTitle(message);
			bar.get(p).get(id).setStyle(BarStyle.valueOf(type));
			bar.get(p).get(id).setColor(BarColor.valueOf(color));
			bar.get(p).get(id).setProgress((float) percent / 100);
		}else{boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.valueOf(type)));
		bar.put(p, boss);
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		}}else {
			boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.valueOf(type)));
			bar.put(p, boss);
			bar.get(p).get(id).setProgress((float) percent / 100);
			bar.get(p).get(id).addPlayer(p);
		}
	}

	@Override
	public void sendBossBarTimer(Player p, String message, int percent, String color, int time, String id, String type) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)){
            Bukkit.getScheduler().cancelTask(task);
            bar.get(p).get(id).setTitle(message);
            bar.get(p).get(id).setStyle(BarStyle.valueOf(type));
			bar.get(p).get(id).setColor(BarColor.valueOf(color));
			bar.get(p).get(id).setProgress((float) percent / 100);
    		Object[] arr = players.toArray();
    		for (Object idd : arr) {
    			task = Bukkit.getScheduler().scheduleSyncDelayedTask(Waze.getInstance(), () -> {
    				bar.get(p).get(id).removePlayer(p);
    				bar.get(p).remove(id);
    				players.remove(idd);
    	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
    		}
		}else{boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.valueOf(type)));
		bar.put(p, boss);
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		players.add(p);
		Object[] arr = players.toArray();
		for (Object idd : arr) {
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(Waze.getInstance(), () -> {
				bar.get(p).get(id).removePlayer(p);
				bar.get(p).remove(id);
				players.remove(idd);
	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
		}}
		}else{
		boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.valueOf(type)));
		bar.put(p, boss);
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		players.add(p);
		Object[] arr = players.toArray();
		for (Object idd : arr) {
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(Waze.getInstance(), () -> {
				bar.get(p).get(id).removePlayer(p);
				bar.get(p).remove(id);
				players.remove(idd);
	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
			
		}}
	}

	@Override
	public void removeBossBar(Player p, String id) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)) {
			bar.get(p).get(id).removePlayer(p);
			bar.get(p).remove(id);

}}}

	@Override
	public String getBossBars(Player p) {
		if(bar.containsKey(p)) {
				return bar.get(p).values().toString();
				//bar.get(p).keySet().iterator().next();
		}
		return null;
		
	}}
