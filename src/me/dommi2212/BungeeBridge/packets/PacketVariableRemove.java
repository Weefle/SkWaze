package me.dommi2212.BungeeBridge.packets;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

import java.io.Serializable;

public class PacketVariableRemove extends BungeePacket implements Serializable {

    private String ID;

    public String getID() {
        return ID;
    }

    public PacketVariableRemove(String ID) {
        super(BungeePacketType.VARIABLE_REMOVE, false);
        this.ID = ID;
    }
}
