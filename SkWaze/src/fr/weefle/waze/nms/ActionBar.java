package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class ActionBar {
	
	Reflection reflection = new Reflection();
	
    public void sendActionBar(Player p, String message) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException {
    	Class<?> IChatBaseComponent = reflection.getNMSClass("IChatBaseComponent");
    	Class<?> ChatMessageType = reflection.getNMSClass("ChatMessageType");
    	Object chat = reflection.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\": \"" + message + "\"}");
    	Object chattype = ChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
    	Object PacketPlayOutChat = reflection.getNMSClass("PacketPlayOutChat").getConstructor(IChatBaseComponent, ChatMessageType).newInstance(chat, chattype);
    	Method sendPacket = reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", reflection.getNMSClass("Packet"));
        sendPacket.invoke(reflection.getConnection(p), PacketPlayOutChat);
    }
}
