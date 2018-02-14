package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class Title {
	
	Reflection reflection = new Reflection();
    public void sendTitle(Player player, String title, String subtitle, int time) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        Class<?> PacketPlayOutTitle = reflection.getNMSClass("PacketPlayOutTitle");
        Class<?> IChatBaseComponent = reflection.getNMSClass("IChatBaseComponent");
        Class<?> ChatSerializer = reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
        Class<?> EnumTitleAction =reflection.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
        Object basetitle = ChatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
        Object endtitle = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent).newInstance(EnumTitleAction.getField("TITLE").get(null), basetitle);
        Object basesubtitle = ChatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
        Object endsubtitle = PacketPlayOutTitle.getConstructor(EnumTitleAction, IChatBaseComponent).newInstance(EnumTitleAction.getField("SUBTITLE").get(null), basesubtitle);
        Object endtime = PacketPlayOutTitle.getConstructor(int.class, int.class, int.class).newInstance(20, time, 20);
        Method sendPacket = reflection.getConnection ( player ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection(player), endtitle );
        sendPacket.invoke(reflection.getConnection(player), endsubtitle);
        sendPacket.invoke(reflection.getConnection(player), endtime);
    }
    }