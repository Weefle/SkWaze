package fr.weefle.waze.data;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.weefle.waze.Waze;
import fr.weefle.waze.data.events.PluginMessageReceiveEvent;
import fr.weefle.waze.data.events.PluginMessageRequestReceiveEvent;

public class ComApiBukkitHandler implements PluginMessageListener{
	
	private Waze instance;
	private List<String> uuids;
	private String channel;
	
	public ComApiBukkitHandler(Waze instance, String channel) {
		this.instance = instance;
		this.channel = channel;
		this.uuids = new ArrayList<>();
		
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(instance, channel);
		Bukkit.getServer().getMessenger().registerIncomingPluginChannel(instance, channel, this);
	}
	
	public void sendMessage(PluginMessage pm) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF(pm.encodeData());
		Bukkit.getServer().sendPluginMessage(instance, channel, out.toByteArray());
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] content) {
		
		if(!channel.equalsIgnoreCase(channel)) {
			return;
			
		}
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(content));
			PluginMessage pm = new PluginMessage();
			pm.decodeData(in.readUTF());
			
			if(uuids.contains(pm.getUUID())) {
				return;
			}
			uuids.add(pm.getUUID());
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					
					uuids.remove(pm.getUUID());
					
				}
			}.runTaskLater(instance, 60);
			
			if(pm.requireResponse()) {
				PluginMessageRequestReceiveEvent event = new PluginMessageRequestReceiveEvent(instance, pm);
				Bukkit.getPluginManager().callEvent(event);
				if(event.getResponse() == null) {
					Bukkit.getLogger().warning("No response set for message: " + pm.getUUID() + "-" + pm.getType());
					return;
				}
				sendMessage(event.getResponse());
				
			}else {
			Bukkit.getPluginManager().callEvent(new PluginMessageReceiveEvent(instance, pm));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getLogger().warning("Failed to handle plugin message !");
		}
		
	}
	
	

}
