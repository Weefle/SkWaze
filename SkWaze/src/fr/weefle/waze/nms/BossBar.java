package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

public interface BossBar {
    public void sendBossBar(Player p, String message, double percent, String color);
    public void removeBossBar(Player p);
    public void removeBossBarAll();
}
