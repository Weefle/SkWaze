package fr.weefle.waze.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.utils.Reflection;

public class Tablist {
	
	Reflection reflection = new Reflection();

    public void setTablist(String header, String footer, Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException {
    	Class<?> packetPlayOutPlayerListHeaderFooter = reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
    	Constructor<?> packetPlayOutPlayerListHeaderFooterConstructor = packetPlayOutPlayerListHeaderFooter.getConstructor();
    	Constructor<?> chatComponentConstructor = reflection.getNMSClass("ChatComponentText").getConstructor(String.class);
    	Object componentHeader = chatComponentConstructor.newInstance(header);
		Object componentFooter = chatComponentConstructor.newInstance(footer);
		Object packet = packetPlayOutPlayerListHeaderFooterConstructor.newInstance();
		Field a = packet.getClass().getDeclaredField("a");
		a.setAccessible(true);
		a.set(packet, componentHeader);

		Field b = packet.getClass().getDeclaredField("b");
		b.setAccessible(true);
		b.set(packet, componentFooter);
		Method sendPacket = reflection.getConnection ( p ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
		sendPacket.invoke (reflection.getConnection(p), packet );
       
    }

    public void removeTablist(Player p, String name) {
        
    }

}
