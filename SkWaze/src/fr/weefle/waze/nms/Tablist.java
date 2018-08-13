package fr.weefle.waze.nms;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;
import fr.weefle.waze.utils.Reflection;

public class Tablist {

    public void setTablist(String header, String footer, Player p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, NoSuchFieldException {
    	Reflection reflection = new Reflection();
    	Class<?> PacketTabHeader = reflection.getNMSClass("PacketTabHeader");
    	Class<?> IChatBaseComponent = reflection.getNMSClass("IChatBaseComponent");
        Class<?> ChatSerializer = reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
        Object tabheader = ChatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + header + "\"}");
        Object tabfooter = ChatSerializer.getMethod("a", String.class).invoke(null, "{\"text\": \"" + footer + "\"}");
        Object packet = PacketTabHeader.getConstructor(IChatBaseComponent, IChatBaseComponent).newInstance(tabheader, tabfooter);
       /* Method sendPacket = reflection.getConnection ( p ).getClass().getMethod ( "sendPacket", reflection.getNMSClass ( "Packet" ));
        sendPacket.invoke (reflection.getConnection(p), packet );*/
        reflection.sendPacket(p, packet);
    	/*IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + name + "\"}");
        IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"Footer\"}");
      
        PacketTabHeader header = new PacketTabHeader(tabTitle, tabFoot);
      
        ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(header);*/
       
    }

    public void removeTablist(Player p, String name) {
        
    }

}
