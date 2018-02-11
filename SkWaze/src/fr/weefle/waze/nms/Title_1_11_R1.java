package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import fr.weefle.waze.Reflection;

public class Title_1_11_R1 implements Title {
	
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
        /*Constructor<?> packetConstructor = PacketPlayOutTitle.getConstructor(int.class, int.class, int.class);
        Object endtime = packetConstructor.newInstance(20, time, 20);*/
        Method sendPacket = reflection.getNMSClass("PlayerConnection").getMethod("sendPacket", reflection.getNMSClass("Packet"));
        sendPacket.invoke(reflection.getConnection(player), endtitle);
        sendPacket.invoke(reflection.getConnection(player), endsubtitle);
        sendPacket.invoke(reflection.getConnection(player), endtime);
    }


   /* @Override
    public void sendTitle(Player p, String title, String subtitle, int time) {
    	try {
    	Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
        Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
        
        IChatBaseComponent basetitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle endtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);

        IChatBaseComponent basesubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle endsubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, basesubtitle);

        PacketPlayOutTitle endtime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, time, 20);

        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endtitle);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endsubtitle);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endtime);
        Class<?> clsPacketPlayOutTitle = Reflection.MINECRAFT.getClass("PacketPlayOutTitle");
        Class<?> clsPacket = Reflection.MINECRAFT.getClass("Packet");
        Class<?> clsIChatBaseComponent = Reflection.MINECRAFT.getClass("IChatBaseComponent");
        Class<?> clsChatSerializer = Reflection.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer");
        Class<?> clsEnumTitleAction = Reflection.MINECRAFT.getClass("PacketPlayOutTitle$EnumTitleAction");
        Object timesPacket = clsPacketPlayOutTitle.getConstructor(int.class, int.class, int.class).newInstance(20, time, 20);
        playerConnection.getClass().getMethod("sendPacket", clsPacket).invoke(playerConnection, timesPacket);
        // Play the title packet
        if (title != null && !title.isEmpty()) {
            Object titleComponent = clsChatSerializer.getMethod("a", String.class).invoke(null, title.toString());
            Object titlePacket = clsPacketPlayOutTitle.getConstructor(clsEnumTitleAction, clsIChatBaseComponent).newInstance(clsEnumTitleAction.getField("TITLE").get(null), titleComponent);
            playerConnection.getClass().getMethod("sendPacket", clsPacket).invoke(playerConnection, titlePacket);
        }
        // Play the subtitle packet
        if (subtitle != null && !subtitle.isEmpty()) {
            Object subtitleComponent = clsChatSerializer.getMethod("a", String.class).invoke(null, subtitle.toString());
            Object subtitlePacket = clsPacketPlayOutTitle.getConstructor(clsEnumTitleAction, clsIChatBaseComponent).newInstance(clsEnumTitleAction.getField("SUBTITLE").get(null), subtitleComponent);
            playerConnection.getClass().getMethod("sendPacket", clsPacket).invoke(playerConnection, subtitlePacket);
        }
    }catch(Throwable e){
    	throw new RuntimeException(e);
    	
    }
    	
}
    public void sendTitleAll(Player p, String title, String subtitle, int time) {
        for (Player player : Bukkit.getOnlinePlayers()) {
        	sendTitle(player, title, subtitle, time);
        }*/
    }
   // }