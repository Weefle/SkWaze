package fr.weefle.waze.discord;

import com.tjplaysnow.discord.object.Bot;
import com.tjplaysnow.discord.object.CommandSpigotManager;
import com.tjplaysnow.discord.object.ThreadSpigot;
import fr.weefle.waze.Waze;

public class DiscordRegister {
	
	private Bot bot;
	private Waze main;
	public DiscordRegister(Waze main) {
		this.main = main;
	}
	
	public void setup(){
		
		bot = new Bot("token", "[WazeBot]");
		bot.setBotThread(new ThreadSpigot(main));
		bot.setConsoleCommandManager(new CommandSpigotManager());
		
	}


}
