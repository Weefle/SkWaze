package fr.weefle.waze.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Tablist {
	
	private Scoreboard sb;
    private Objective ob;

    public void createTablist(String name, Player p) {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        ob = sb.registerNewObjective(name, "dummy");
        ob.setDisplayName(name);
        DisplaySlot dis = DisplaySlot.PLAYER_LIST;
        ob.setDisplaySlot(dis);
        //ob.getScore(score).setScore(line);
        p.setScoreboard(sb);
    }
    
    public void changeTablist(String name, Player p) {
    	if(ob != null) {
    		ob.setDisplayName(name);
    		p.setScoreboard(sb);
    	}
 
    }

    public void removeTablist(Player p, String name) {
        p.getScoreboard().getObjective(name).unregister();
    }

}
