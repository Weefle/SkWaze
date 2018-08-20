package fr.weefle.waze.discord;

import java.util.List;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PingCommand extends ProgramCommand {

	public String getLabel() {
		return "ping";
	}
	
	public String getDescription() {
		return "A simple Ping Pong command";
	}

	protected boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
		
		channel.sendMessage("Pong").queue(delete());
		
		return true;
	}

}
