package fr.weefle.waze.nms;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;

public class SideBar {
	
	ArrayList<BPlayerBoard> boards = new ArrayList<>();
	

    public void setSideBar(String name, Player p, String score, int line) {
    	if(Netherboard.instance().getBoard(p) == null) {
    		BPlayerBoard board = Netherboard.instance().createBoard(p, name);
    		board.set(score, line);
    }else{
    	BPlayerBoard board = Netherboard.instance().getBoard(p);
    	board.setName(name);
    	board.set(score, line);
    	}
    }
    
    public void removeLineSideBar(Player p, int line) {
    	BPlayerBoard board = Netherboard.instance().getBoard(p);
    	board.remove(5);
    }

    public void removeSideBar(Player p) {
    	BPlayerBoard board = Netherboard.instance().getBoard(p);
    	board.delete();
    }
}
