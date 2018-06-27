package fr.weefle.waze.discord;

import java.util.List;
import com.tjplaysnow.discord.object.ProgramCommand;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class PingCommand extends ProgramCommand {

	@Override
	public String getLabel() {
		return "ping";
	}
	
	@Override
	public String getDescription() {
		return "A simple Ping Pong command";
	}

	@Override
	protected boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
		
		channel.sendMessage("Pong").queue(delete());
		
		return true;
	}

}
