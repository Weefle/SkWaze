package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

public interface ActionBarAPI {
	
	public void sendActionBar(Player player, String message) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException;
	
}
