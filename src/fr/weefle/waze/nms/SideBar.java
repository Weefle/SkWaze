package fr.weefle.waze.nms;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SideBar {

	private final Map<UUID, FastBoard> boards = new HashMap<>();
	

    public void setSideBar(String name, Player p, String score, int line) {

    	if(boards.get(p.getUniqueId()) != null && !boards.get(p.getUniqueId()).isDeleted()){

			boards.get(p.getUniqueId()).updateTitle(name);
			boards.get(p.getUniqueId()).updateLine(line, score);

		} else{

			FastBoard board = new FastBoard(p);

			board.updateTitle(name);
			board.updateLine(line, score);

			boards.put(p.getUniqueId(), board);

    	}
    }
    
    public void removeLineSideBar(Player p, int line) {
    	if(!boards.get(p.getUniqueId()).isDeleted()) {
			boards.get(p.getUniqueId()).removeLine(line);
		}
    }

    public void removeSideBar(Player p) {
		if(!boards.get(p.getUniqueId()).isDeleted()) {
			boards.get(p.getUniqueId()).delete();
		}
    }
}
