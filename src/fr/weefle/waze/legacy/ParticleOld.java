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
	
	public void createHelix(Player player, String particles, Location location, float xoff, float yoff, float zoff, float data, int number) {
        int radius = 2;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            location.setX(location.getX()+x);
            location.setY(location.getY()+y);
            location.setZ(location.getZ()+z);
            ParticleEffect part = ParticleEffect.valueOf(particles);
    		part.display(xoff, yoff, zoff, data, number, location, player);
        }
    }
	
	

}
