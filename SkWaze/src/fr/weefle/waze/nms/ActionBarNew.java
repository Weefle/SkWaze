package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class ActionBarNew implements ActionBar{
	
	Reflection reflection = new Reflection();
	
    public void sendActionBar(Player player, String message) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException {
    	Class<?> clsIChatBaseComponent = reflection.getNMSClass("IChatBaseComponent");
        Class<?> clsChatMessageType = reflection.getNMSClass("ChatMessageType");
        Object chatBaseComponent = clsIChatBaseComponent.getClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + message + "\"}");
        //Object chatBaseComponent = reflection.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\": \"" + message + "\"}");
        Object chatMessageType = clsChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
        Object packetPlayOutChat = reflection.getNMSClass("PacketPlayOutChat").getConstructor(clsIChatBaseComponent, clsChatMessageType).newInstance(chatBaseComponent, chatMessageType);
        Method sendPacket = reflection.getConnection ( player ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection(player), packetPlayOutChat );
    }
}
