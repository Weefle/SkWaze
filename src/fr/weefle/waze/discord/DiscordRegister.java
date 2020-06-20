package fr.weefle.waze.discord;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class DiscordRegister {
	
	public DiscordRegister(String token) {

			JDA api = null;
			try {
				api = new JDABuilder(token).build();
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			api.addEventListener(new PingCommand());

	}
	
	}
	
	
