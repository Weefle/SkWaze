package fr.weefle.waze.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class Particles {
	
	Reflection reflection = new Reflection();
	
    public void sendParticles(Player player, String particles, boolean visible, float x, float y, float z, float xoff, float yoff, float zoff, float data, int number, int...is) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
    	Class<?> PacketPlayOutWorldParticles = reflection.getNMSClass("PacketPlayOutWorldParticles");
    	Class<?> EnumParticle = reflection.getNMSClass("EnumParticle");
        Constructor<?> packetConstructor = PacketPlayOutWorldParticles.getConstructor(EnumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
        Object packet = packetConstructor.newInstance(EnumParticle.getField(particles).get(null), visible, x, y, z, xoff, yoff, zoff, data, number, is);
        Method sendPacket = reflection.getConnection ( player ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection ( player ), packet );
    }
    }