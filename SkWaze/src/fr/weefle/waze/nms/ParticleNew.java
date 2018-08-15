package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import fr.weefle.waze.utils.Reflection;

public class ParticleNew implements ParticleAPI {
	
	Reflection reflection = new Reflection();
	
    public void sendParticles(Player player, String particles, Location location, float xoff, float yoff, float zoff, float data, int number) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
    	/*Class<?> PacketPlayOutWorldParticles = reflection.getNMSClass("PacketPlayOutWorldParticles");
    	Class<?> EnumParticle = reflection.getNMSClass("EnumParticle");
        Constructor<?> packetConstructor = PacketPlayOutWorldParticles.getConstructor(EnumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
        Object packet = packetConstructor.newInstance(EnumParticle.getField(particles).get(null), visible, (float) location.getBlockX(), (float) location.getBlockY(), (float) location.getBlockZ(), xoff, yoff, zoff, data, number, is);
        Method sendPacket = reflection.getConnection ( player ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection ( player ), packet );*/
    	player.getWorld().spawnParticle(Particle.valueOf(particles), location, number, xoff, yoff, zoff, data);
    }
    }