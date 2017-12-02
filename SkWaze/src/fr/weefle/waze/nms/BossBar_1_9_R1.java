package fr.weefle.waze.nms;

import fr.weefle.waze.Waze;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar_1_9_R1 implements BossBar {
    private org.bukkit.boss.BossBar bar = null;
    private int task;
    private Waze m;
    public BossBar_1_9_R1(Waze m) {
        this.m = m;
    }
    @Override
    public void sendBossBar(Player p, String message, double percent, String color) {
        BarColor c = BarColor.valueOf(color);
        if(bar != null){
            bar.setTitle(message);
            bar.setColor(c);
            bar.setStyle(BarStyle.SOLID);
            bar.setProgress(percent);
            bar.addPlayer(p);
        }else{
            bar = Bukkit.createBossBar(message, c, BarStyle.SOLID);
            bar.setProgress(percent);
            bar.addPlayer(p);
        }
    }

    @Override
    public void removeBossBar(Player p) {
        if(bar != null){
            bar.removePlayer(p);
            bar = null;
        }
    }

    @Override
    public void removeBossBarAll() {
        if(bar != null){
            bar.removeAll();
            bar = null;
        }
    }

    @Override
    public void sendBossBarTime(Player p, String message, double percent, String color, int time) {
        BarColor c = BarColor.valueOf(color);
        if(bar != null){
            Bukkit.getScheduler().cancelTask(task);
            bar.setTitle(message);
            bar.setColor(c);
            bar.setStyle(BarStyle.SOLID);
            bar.setProgress(percent);
            bar.addPlayer(p);
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
                bar.removePlayer(p);
                bar = null;
                Bukkit.getScheduler().cancelTask(task);}, time);
        }else{
            bar = Bukkit.createBossBar(message, c, BarStyle.SOLID);
            bar.setProgress(percent);
            bar.addPlayer(p);
            task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
                bar.removePlayer(p);
                bar = null;
                Bukkit.getScheduler().cancelTask(task);}, time);
        }

    }
}
