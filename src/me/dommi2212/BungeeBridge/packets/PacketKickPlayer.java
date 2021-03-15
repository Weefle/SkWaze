package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to kick a player from the network.
 */
@SuppressWarnings("serial")
public class PacketKickPlayer extends BungeePacket implements Serializable {
	
	private UUID uuid;
	private String message;
	
	/**
	 * Instantiates a new PacketKickPlayer.
	 *
	 * @param uuid uuid
	 * @param message message
	 */
	public PacketKickPlayer(UUID uuid, String message) {
		super(BungeePacketType.KICKPLAYER, false);
		this.uuid = uuid;
		this.message = message;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return uuid
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
}
