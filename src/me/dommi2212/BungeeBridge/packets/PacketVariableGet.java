package me.dommi2212.BungeeBridge.packets;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

import java.io.Serializable;

    public class PacketVariableGet extends BungeePacket implements Serializable {

        private String ID;

        public String getID() {
            return ID;
        }

        public PacketVariableGet(String ID) {
            super(BungeePacketType.VARIABLE_GET, true);
            this.ID = ID;
        }
    }