package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get a player's uuid by their name.
 * 
 * Returned: UUID uuid
 */
@SuppressWarnings("serial")
public class PacketGetPlayerUUID extends BungeePacket implements Serializable {
	
	private String name;
	
	/**
	 * Instantiates a new PacketGetPlayerUUID.
	 *
	 * @param name name
	 */
	public PacketGetPlayerUUID(String name) {
		super(BungeePacketType.GETPLAYERUUID, true);
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
}
