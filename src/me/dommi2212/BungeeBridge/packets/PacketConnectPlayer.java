package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to connect a player to a server.
 * 
 * Returned: ConnectResult result
 */
@SuppressWarnings("serial")
public class PacketConnectPlayer extends BungeePacket implements Serializable {
	
	private UUID uuid;
	private String server;
	
	/**
	 * Instantiates a new PacketConnectPlayer.
	 *
	 * @param uuid the player's uuid.
	 * @param server server to connect.
	 */
	public PacketConnectPlayer(UUID uuid, String server) {
		super(BungeePacketType.CONNECTPLAYER, true);
		this.uuid = uuid;
		this.server = server;		
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
	 * Gets the server.
	 *
	 * @return server
	 */
	public String getServer() {
		return server;
	}
	
}
