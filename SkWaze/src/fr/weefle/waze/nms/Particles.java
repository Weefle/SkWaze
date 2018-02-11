package fr.weefle.waze.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class Particles {
	
	Reflection reflection = new Reflection();
	
    public void sendParticles(Player player, String particles, float x, float y, float z, float xoff, float yoff, float zoff, float data, int number) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
    	Class<?> PacketPlayOutWorldParticles = reflection.getNMSClass("PacketPlayOutWorldParticles");
    	Class<?> EnumParticle = reflection.getNMSClass("EnumParticle");
        Constructor<?> packetConstructor = PacketPlayOutWorldParticles.getConstructor(EnumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
        Object packet = packetConstructor.newInstance(particles, x, y, z, xoff, yoff, zoff, data, number);
        Method sendPacket = reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", reflection.getNMSClass("Packet"));
        sendPacket.invoke(reflection.getConnection(player), packet);
    }
}
