package fr.weefle.waze.skwrapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.rhaz.sockets.socket4mc.Socket4Bukkit.Client.ClientSocketJSONEvent;

public class SkWrapperReceiver implements Listener {
	
	private static String message;
	/*private BukkitTask task = null;
	private Waze main;
	public SkWrapperReceiver(Waze main) {
		this.main = main;
	}*/

	@EventHandler
    public void onSocketMessage(ClientSocketJSONEvent e) {
		String channel = e.getChannel(); // The channel name
	    
        if(channel.equals("SkWrapper-list")) {
        	/*setTask(Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
				
				@Override
				public void run() {
					String message = e.getExtraString("message");
					SkWrapperReceiver.message = message;
					
				}
			}, 0L, 1L));*/
        	
        	String message = e.getExtraString("message");
			SkWrapperReceiver.message = message;
        }
	}
	
	public static String getServers() {
		
		return message;
		
	}

	/*public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask bukkitTask) {
		this.task = bukkitTask;
	}*/

}
