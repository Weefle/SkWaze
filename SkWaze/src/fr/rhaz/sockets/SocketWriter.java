package fr.rhaz.sockets;

import java.io.IOException;
import fr.rhaz.sockets.utils.JSONMap;

public interface SocketWriter {
	@Deprecated
	public void write(String data);
	public void write(String channel, String data);
	public void write(String channel, JSONMap data);
	public IOException close();
	public boolean isEnabled();
	public boolean isHandshaked();
	public boolean isConnectedAndOpened();
}
