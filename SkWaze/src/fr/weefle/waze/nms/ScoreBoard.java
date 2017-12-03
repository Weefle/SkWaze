package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

public interface ScoreBoard {
    public void createScoreBoard(String name, Player p, String score, String type, int line, String slot);
    public void removeScoreBoard(Player p, String name);
}
