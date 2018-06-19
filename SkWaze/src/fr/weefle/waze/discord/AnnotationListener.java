package fr.weefle.waze.discord;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class AnnotationListener {
	
	@EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
		
    }
  
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
    	
    }

}
