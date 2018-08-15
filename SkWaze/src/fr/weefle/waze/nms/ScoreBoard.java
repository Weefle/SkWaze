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
    	if(scobo.containsKey(p)) {
    			DisplaySlot dis = DisplaySlot.valueOf(slot);
    			//if(!scobo.get(p).getScores(p.getName()).contains(obj.get(line).getScore(p.getName()))) {
    				scobo.get(p).getObjective(dis).getScore(score).setScore(line);
            		p.setScoreboard(scobo.get(p));
    		/*	}else {
    				p.sendMessage("test");
    				scobo.get(p).resetScores(p.getName());
    				scobo.get(p).getObjective(dis).getScore(score).setScore(line);
            		p.setScoreboard(scobo.get(p));
    			}*/
    	}else {
    	scobo.put(p, Bukkit.getScoreboardManager().getNewScoreboard());
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
    }}
    
   /* public void changeScore(String slot, Player p, String score, int line) {
    	if(ob != null) {
    		ob.getScore(name).setScore(line);
    		p.setScoreboard(sb);
    	}
    	if(scobo.containsKey(p) && !obj.isEmpty()) {
    		DisplaySlot dis = DisplaySlot.valueOf(slot);
    		scobo.get(p).getObjective(dis).getScore(score).setScore(line);
    	}
 
    }
    
    public void removeScore(String name, Player p) {
    	//here remove the score from a line
    	if(scobo.containsKey(p) && !obj.isEmpty()) {
    		obj.remove(name);
    	}
    }*/

    public void removeScoreBoard(Player p, String name) {
    	scobo.get(p).resetScores(p.getName());
    	obj.removeAll(obj);
        p.getScoreboard().getObjective(name).unregister();
    }
}
