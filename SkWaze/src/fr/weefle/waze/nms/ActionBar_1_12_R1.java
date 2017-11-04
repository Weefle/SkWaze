package fr.weefle.waze.nms;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar_1_12_R1 implements ActionBar {

    @Override
    public void sendActionBar(Player p, String message) {

        IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");

        PacketPlayOutChat bar = new PacketPlayOutChat(chat, ChatMessageType.a((byte)2));

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);

    }

}
