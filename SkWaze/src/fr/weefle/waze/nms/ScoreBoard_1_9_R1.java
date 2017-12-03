package fr.weefle.waze.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard_1_9_R1 implements ScoreBoard {
    private Scoreboard sb;
    private Objective ob;

    @Override
    public void createScoreBoard(String name, Player p, String score, String type, int line, String slot) {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        ob = sb.registerNewObjective(name, type);
        ob.setDisplayName(name);
        DisplaySlot dis = DisplaySlot.valueOf(slot);
        ob.setDisplaySlot(dis);
        ob.getScore(score).setScore(line);
        p.setScoreboard(sb);
    }

    @Override
    public void removeScoreBoard(Player p, String name) {
        p.getScoreboard().getObjective(name).unregister();
    }
}
