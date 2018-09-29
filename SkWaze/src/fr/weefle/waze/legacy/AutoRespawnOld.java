package fr.weefle.waze.legacy;

import org.bukkit.entity.Player;

import fr.weefle.waze.Waze;
import fr.weefle.waze.nms.AutoRespawnAPI;
import fr.weefle.waze.utils.Reflection;

public class AutoRespawnOld implements AutoRespawnAPI {
	
	Reflection reflection = new Reflection();
	
	public void respawn(Player p) {
		Waze.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Waze.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(p.isDead()) {
					try {
						Object nmsplayer = p.getClass().getMethod("getHandle").invoke(p);
					Object packet = Class.forName(nmsplayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
					Class<?> enumclass = Class.forName(nmsplayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand$EnumClientCommand");
					
			        for(Object obj : enumclass.getEnumConstants()) {
			        	if(obj.toString().equals("PERFORM_RESPAWN")) {
			        		packet = packet.getClass().getConstructor(enumclass).newInstance(obj);
			        	}
			        }
			        reflection.getConnection(p).getClass().getMethod("a", packet.getClass()).invoke(reflection.getConnection(p), packet);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
	});

}
}
