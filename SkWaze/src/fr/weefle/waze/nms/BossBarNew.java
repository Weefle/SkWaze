package fr.weefle.waze.nms;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import fr.weefle.waze.Waze;

public class BossBarNew implements BossBarAPI {
	
	private HashMap<String, BossBar> bar = new HashMap<>();
	private int task;
    private Waze m;
    public BossBarNew(Waze m) {
        this.m = m;
    }

	@Override
	public void sendBossBar(Player p, String message, double percent, String color, String id) {
		if(bar.containsKey(id)) {
			bar.get(id).setTitle(message);
			bar.get(id).setColor(BarColor.valueOf(color));
			bar.get(id).setProgress(percent);
		}else {
			bar.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
			bar.get(id).setProgress(percent);
			bar.get(id).addPlayer(p);
			//p.sendMessage("" + bar.values());
		}
	}

	@Override
	public void sendBossBarTimer(Player p, String message, double percent, String color, int time, String id) {
		if(bar.containsKey(id)){
            Bukkit.getScheduler().cancelTask(task);
            bar.get(id).setTitle(message);
            bar.get(id).setColor(BarColor.valueOf(color));
            bar.get(id).setStyle(BarStyle.SOLID);
            bar.get(id).setProgress(percent);
            bar.get(id).addPlayer(p);
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
            	bar.remove(id).removePlayer(p);
                Bukkit.getScheduler().cancelTask(task);}, time);
        }else{
        	bar.put(id, Bukkit.createBossBar(message, BarColor.valueOf(color), BarStyle.SOLID));
            bar.get(id).setProgress(percent);
            bar.get(id).addPlayer(p);
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
            	bar.remove(id).removePlayer(p);
                Bukkit.getScheduler().cancelTask(task);}, time);
        }
	}

	@Override
	public void removeBossBar(Player p, String id) {
		if(bar.containsKey(id)) {
			bar.remove(id).removePlayer(p);
			//p.sendMessage("" + bar.values());
		}
	}

}
