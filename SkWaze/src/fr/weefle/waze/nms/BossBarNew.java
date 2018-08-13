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
	//private ArrayList<String> ids = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	private int task;
    private Waze m;
    public BossBarNew(Waze m) {
        this.m = m;
    }

	@Override
	public void sendBossBar(Player p, String message, int percent, String color, String id) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)) {
			bar.get(p).get(id).setTitle(message);
			bar.get(p).get(id).setColor(BarColor.valueOf(color));
			bar.get(p).get(id).setProgress((float) percent / 100);
			//bar.put(p, bar.get(p)).get(id).setTitle(message);
			/*bar.get(id).setColor(BarColor.valueOf(color));
			bar.get(id).setTitle(message);
			bar.get(id).setProgress(percent);*/
		}else{boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		bar.put(p, boss);
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		}}else {
			//envoie à tous les joueurs?
			 /* for(Player i : Bukkit.getOnlinePlayers()){
				  bar.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
					bar.get(id).setProgress(percent);
					bar.get(id).addPlayer(i);
			  }*/
			boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
			bar.put(p, boss);
			bar.get(p).get(id).setProgress((float) percent / 100);
			bar.get(p).get(id).addPlayer(p);
			/*bar.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
			bar.get(id).setProgress(percent);
			bar.get(id).addPlayer(p);*/
			//p.sendMessage("" + bar.values());
		}
	}

	@Override
	public void sendBossBarTimer(Player p, String message, int percent, String color, int time, String id) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)){
            Bukkit.getScheduler().cancelTask(task);
            /*bar.get(id).setTitle(message);
            bar.get(id).setColor(BarColor.valueOf(color));
            bar.get(id).setStyle(BarStyle.SOLID);
            bar.get(id).setProgress(percent);
            bar.get(id).addPlayer(p);*/
            bar.get(p).get(id).setTitle(message);
			bar.get(p).get(id).setColor(BarColor.valueOf(color));
			bar.get(p).get(id).setProgress((float) percent / 100);
            //players.add(p);
    		Object[] arr = players.toArray();
    		for (Object idd : arr) {
    			
    			//p.sendMessage("avant: " + bar.values());
    			
    			task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
    				bar.get(p).get(id).removePlayer(p);
    				bar.get(p).remove(id);
    				players.remove(idd);
    				//bar.remove(p);
    				//p.sendMessage("apres: " + bar.values());
    	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
    			
    		}
		}else{boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		bar.put(p, boss);
		//p.sendMessage("" + bar.values());
		//bar.get(p).put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		players.add(p);
		//String[] array = new String[ids.size()];
		Object[] arr = players.toArray();
		for (Object idd : arr) {
			
			//p.sendMessage("avant: " + bar.values());
			
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
				bar.get(p).get(id).removePlayer(p);
				bar.get(p).remove(id);
				players.remove(idd);
				//bar.remove(p);
				//p.sendMessage("apres: " + bar.values());
	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
			
		}}}else{
        	/*bar.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
            bar.get(id).setProgress(percent);
            bar.get(id).addPlayer(p);
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
            	bar.remove(id).removePlayer(p);
                Bukkit.getScheduler().cancelTask(task);}, time);*/
	//}
		//bar.put(p, bar.get(p)).put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		boss.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		bar.put(p, boss);
		//p.sendMessage("" + bar.values());
		//bar.get(p).put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
		bar.get(p).get(id).setProgress((float) percent / 100);
		bar.get(p).get(id).addPlayer(p);
		players.add(p);
		//String[] array = new String[ids.size()];
		Object[] arr = players.toArray();
		for (Object idd : arr) {
			
			//p.sendMessage("avant: " + bar.values());
			
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
				bar.get(p).get(id).removePlayer(p);
				bar.get(p).remove(id);
				players.remove(idd);
				//bar.remove(p);
				//p.sendMessage("apres: " + bar.values());
	            Bukkit.getScheduler().cancelTask(task);}, time * 20);
			
		}}
		/*ids.add(id);
		//String[] array = new String[ids.size()];
		Object[] arr = ids.toArray();
		for (Object idd : arr) {
			
			p.sendMessage("avant: " + bar.values());
			
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
				bar.get(p).get(id).removePlayer(p);
				bar.get(p).remove(id);
				ids.remove(idd);
				p.sendMessage("apres: " + bar.values());
	            Bukkit.getScheduler().cancelTask(task);}, time);
			
		}*/
		/*task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
			bar.get(p).get(id).removePlayer(p);
			bar.get(p).remove(id);
			ids.remove(id);
			//p.sendMessage("" + bar.values());
            Bukkit.getScheduler().cancelTask(task);}, time);*/
	}

	@Override
	public void removeBossBar(Player p, String id) {
		if(bar.containsKey(p)) {
		if(bar.get(p).containsKey(id)) {
			bar.get(p).get(id).removePlayer(p);
			bar.get(p).remove(id);
			//p.sendMessage("" + bar.values());

}}}}
