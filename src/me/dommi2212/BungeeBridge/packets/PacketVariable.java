package me.dommi2212.BungeeBridge.packets;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

import java.io.Serializable;

public class PacketVariable extends BungeePacket implements Serializable {

    private String ID;
    private Object object;

    public String getID() {
        return ID;
    }

    public Object getObject() {
        return object;
    }

    public PacketVariable(String ID, Object object) {
        super(BungeePacketType.VARIABLE, false);
        this.ID = ID;
        this.object = object;
    }
}
