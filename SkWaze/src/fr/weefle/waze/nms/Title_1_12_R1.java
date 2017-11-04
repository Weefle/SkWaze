package fr.weefle.waze.nms;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_1_12_R1 implements Title {
    @Override
    public void sendTitle(Player p, String title, String subtitle, int time) {
        IChatBaseComponent basetitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle endtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);

        IChatBaseComponent basesubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle endsubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, basesubtitle);

        PacketPlayOutTitle endtime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, time, 20);

        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endtitle);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endsubtitle);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(endtime);
    }
}
