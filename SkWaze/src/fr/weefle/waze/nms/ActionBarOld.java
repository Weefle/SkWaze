package fr.weefle.waze.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class ActionBarOld implements ActionBar {
	
	Reflection reflection = new Reflection();
	
    public void sendActionBar(Player player, String message) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException {
    	Class< ? > packetPlayOutChat = reflection.getNMSClass ( "PacketPlayOutChat" );
        Constructor< ? > packetConstructor = packetPlayOutChat.getConstructor ( reflection.getNMSClass ( "IChatBaseComponent" ), byte.class );
        Class< ? > ichat = reflection.getNMSClass ( "IChatBaseComponent" );
        Class< ? > chatSerializer = ichat.getClasses ( )[ 0 ];
        Method csA = chatSerializer.getMethod ( "a", String.class );
        Object component = csA.invoke ( chatSerializer, "{\"text\": \"" + message + "\"}" );
        Object packet = packetConstructor.newInstance ( component, ( byte ) 2 );
        Method sendPacket = reflection.getConnection ( player ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection(player), packet );
    }
}
