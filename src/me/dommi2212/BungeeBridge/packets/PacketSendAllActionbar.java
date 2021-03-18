package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to send a actionbar to all players.
 */
@SuppressWarnings("serial")
public class PacketSendAllActionbar extends BungeePacket implements Serializable {

    private String actionbar;

    /**
     * Instantiates a new PacketSendActionbar.
     *
     * @param actionbar actionbar
     */
    public PacketSendAllActionbar(String actionbar) {
        super(BungeePacketType.SENDALLACTIONBAR, false);
        this.actionbar = actionbar;
    }

    /**
     * Gets the actionbar.
     *
     * @return actionbar
     */
    public String getActionbar() {
        return actionbar;
    }
}
