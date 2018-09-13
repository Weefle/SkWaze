package fr.weefle.waze.legacy;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.weefle.waze.nms.ParticleAPI;
import fr.weefle.waze.utils.ParticleEffect;

public class ParticleOld implements ParticleAPI {

	@Override
	public void sendParticles(Player player, String particles, Location location, float xoff,
			float yoff, float zoff, float data, int number)
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		ParticleEffect part = ParticleEffect.valueOf(particles);
		part.display(xoff, yoff, zoff, data, number, location, player);
		
	}
	
	

}
