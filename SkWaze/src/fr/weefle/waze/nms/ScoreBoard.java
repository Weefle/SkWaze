package fr.weefle.waze.nms;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard {
	
	private HashMap<Player, Scoreboard> scobo = new HashMap<>();
    /*private Scoreboard sb;
    private Objective ob;*/
	private ArrayList<Objective> obj = new ArrayList<>();

    public void createScoreBoard(String name, Player p, String score, String type, int line, String slot) {
    	scobo.put(p, Bukkit.getScoreboardManager().getNewScoreboard());
    	scobo.get(p).registerNewObjective(name, type);
    	obj.add(scobo.get(p).registerNewObjective(name, type));
    	obj.get(line).setDisplayName(name);
    	DisplaySlot dis = DisplaySlot.valueOf(slot);
    	obj.get(line).setDisplaySlot(dis);
    	obj.get(line).getScore(score).setScore(line);
    	p.setScoreboard(scobo.get(p));
        /*sb = Bukkit.getScoreboardManager().getNewScoreboard();
        ob = sb.registerNewObjective(name, type);
        ob.setDisplayName(name);
        DisplaySlot dis = DisplaySlot.valueOf(slot);
        ob.setDisplaySlot(dis);
        ob.getScore(score).setScore(line);
        p.setScoreboard(sb);*/
    }
    
    public void changeScore(String slot, Player p, String score, int line) {
    	/*if(ob != null) {
    		ob.getScore(name).setScore(line);
    		p.setScoreboard(sb);
    	}*/
    	if(scobo.containsKey(p) && !obj.isEmpty()) {
    		DisplaySlot dis = DisplaySlot.valueOf(slot);
    		scobo.get(p).getObjective(dis).getScore(score).setScore(line);
    	}
 
    }
    
    public void removeScore() {
    	//here remove the score from a line
    }

    public void removeScoreBoard(Player p, String name) {
        p.getScoreboard().getObjective(name).unregister();
    }
}
