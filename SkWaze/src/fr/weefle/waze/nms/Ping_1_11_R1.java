package fr.weefle.waze.nms;

import net.minecraft.server.v1_11_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping_1_11_R1 implements Ping {
    @Override
    public int getPing(Player p) {
        CraftPlayer pl = (CraftPlayer) p;
        EntityPlayer pla = pl.getHandle();
        return pla.ping;
    }
}
