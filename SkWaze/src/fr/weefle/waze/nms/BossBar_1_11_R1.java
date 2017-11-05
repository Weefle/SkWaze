package fr.weefle.waze.nms;

import fr.weefle.waze.Waze;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar_1_11_R1 implements BossBar {
    private org.bukkit.boss.BossBar bar;
    private int task;
    private Waze m;
    public BossBar_1_11_R1(Waze m) {
        this.m = m;
    }
    @Override
    public void sendBossBar(Player p, String message, double percent, String color) {
        BarColor c = BarColor.valueOf(color);
        bar = Bukkit.createBossBar(message, c, BarStyle.SOLID);
        bar.setProgress(percent);
        bar.addPlayer(p);
    }

    @Override
    public void removeBossBar(Player p) {
        bar.removePlayer(p);
    }

    @Override
    public void removeBossBarAll() {
        bar.removeAll();
    }

    @Override
    public void sendBossBarTime(Player p, String message, double percent, String color, int time) {
        BarColor c = BarColor.valueOf(color);
        bar = Bukkit.createBossBar(message, c, BarStyle.SOLID);
        bar.setProgress(percent);
        bar.addPlayer(p);
        task = Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> {
            bar.removePlayer(p);
            Bukkit.getScheduler().cancelTask(task);}, time);
    }
}
