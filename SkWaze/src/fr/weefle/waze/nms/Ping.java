package fr.weefle.waze.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class Ping {
	
	Reflection reflection = new Reflection();
	
    public int getPing(Player p) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	Method getHandle = p.getClass().getMethod("getHandle");
        Object nmsPlayer = getHandle.invoke(p);
        Field field = nmsPlayer.getClass().getField("ping");
        int ping = field.getInt(nmsPlayer);
        return ping;
    }
}
