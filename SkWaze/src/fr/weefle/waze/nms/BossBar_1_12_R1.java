package fr.weefle.waze.nms;

import fr.weefle.waze.nms.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar_1_12_R1 implements BossBar {
    public org.bukkit.boss.BossBar bar;
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
}
