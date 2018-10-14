package fr.weefle.waze.skwrapper;

import org.bukkit.event.Listener;

import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class SkWrapperSender implements Listener {
	
	public void sendPing() {
		String request = "Successfully connected to the server!"; //1  
		PacketCustom packet = new PacketCustom("SkWrapper-msg", (Object) request); //2  
		String answer = (String) packet.send(); //3  
		System.out.println(answer); //4  
	   /* JSONMap map = new JSONMap(
	        "message", "Successfully connected to the server!"
	    );

	    Socket4Bukkit.getClient().write("SkWrapper-msg", map);
	}
		@EventHandler
		public void onHandshake(ClientSocketHandshakeEvent e){
		    sendPing();*/
		}

}
