package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ParticleAPI {
	
	public void sendParticles(Player player, String particles, Location location, float xoff, float yoff, float zoff, float data, int number) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;

}
