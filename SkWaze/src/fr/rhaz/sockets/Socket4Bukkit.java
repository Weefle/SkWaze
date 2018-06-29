package fr.rhaz.sockets;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import fr.rhaz.sockets.client.SocketClient;
import fr.rhaz.sockets.client.SocketClientApp;
import fr.rhaz.sockets.server.SocketMessenger;
import fr.rhaz.sockets.server.SocketServerApp;
import fr.rhaz.sockets.utils.JSONMap;

public class Socket4Bukkit extends JavaPlugin {
	private static Socket4Bukkit i;
	private Configuration config;
	private static SocketClient client;
	public static Server sapp;
	public static Client capp;
	
	@Override
	public void onEnable(){
		i = this;
		
		config = loadConfig("config.yml");
		sapp = new Server();
		capp = new Client();
		
		start();
	}
	
	public static Socket4Bukkit get() {
		return i;
	}
	
	public static SocketClient getClient() {
		return client;
	}
	
	public void start(){
		client = new SocketClient(capp, 
				config.getString("name", "myspigot").toLowerCase(), 
				config.getString("host", "localhost"), 
				config.getInt("port", 25575), 
				config.getString("password", "")
		);
		
		client.start();
	}
	
	public boolean stop(){
		IOException err = client.interrupt();
		if(err != null){
			getLogger().warning("Could not stop socket client on port "+client.getPort());
			err.printStackTrace();
			return false;
		} else {
			getLogger().info("Successfully stopped socket client on port "+client.getPort());
			return true;
		}
	}
	
	public void restart(){
		if(stop()) start();
	}

	public Configuration loadConfig(String name){
		if (!getDataFolder().exists()) getDataFolder().mkdir();
        File file = new File(getDataFolder(), name);
        if (!file.exists()) saveResource(name, false);
        return YamlConfiguration.loadConfiguration(file);
	}
	
	public static class Server extends SocketServerApp {
		
		public static class ServerSocketConnectEvent extends Event {
			private SocketMessenger mess;
			
			public ServerSocketConnectEvent(SocketMessenger mess){
				this.mess = mess;
			}
			
			public SocketMessenger getMessenger(){
				return mess;
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ServerSocketDisconnectEvent extends Event {
			private SocketMessenger mess;
			
			public ServerSocketDisconnectEvent(SocketMessenger mess){
				this.mess = mess;
			}
			
			public SocketMessenger getMessenger(){
				return mess;
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ServerSocketJSONEvent extends Event {
			private SocketMessenger mess;
			private Map<String, Object> map;
			
			public ServerSocketJSONEvent(SocketMessenger mess, Map<String, Object> map){
				this.mess = mess;
				this.map = map;
			}
			
			public String getChannel(){
				return (String) map.get("channel");
			}
			
			@Deprecated
			public String getData(){
				return getExtraString("data");
			}
			
			@SuppressWarnings("unchecked")
			public <T> T getExtra(String key, Class<T> type) {
				return (T) map.get(key);
			}
			
			public String getExtraString(String key) {
				return getExtra(key, String.class);
			}
			
			public int getExtraInt(String key) {
				return getExtra(key, int.class);
			}
			
			@SuppressWarnings("unchecked")
			public Map<String, Object> getExtraMap(String key) {
				return getExtra(key, Map.class);
			}
			
			@Deprecated
			public Map<String, Object> getMap(){
				return map;
			}
			
			public String getName(){
				return getExtraString("name");
			}
			
			public SocketMessenger getMessenger(){
				return mess;
			}
			
			public void write(Object... entries) {
				mess.write(getChannel(), new JSONMap(entries));
			}
			
			public void write(JSONMap data) {
				mess.write(getChannel(), data);
			}
			
			public void write(String data){
				mess.write(getChannel(), data);
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ServerSocketHandshakeEvent extends Event {
			private String name;
			private SocketMessenger mess;
			
			public ServerSocketHandshakeEvent(SocketMessenger mess, String name){
				this.mess = mess;
				this.name = name;
			}
			
			public SocketMessenger getMessenger(){
				return mess;
			}
			
			public String getName(){
				return name;
			}
			
			public void write(String channel, JSONMap data) {
				mess.write(channel, data);
			}
			
			public void write(String channel, String data) {
				mess.write(channel, data);
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
	
		@Override
		public void run(Runnable run){
			plugin().getServer().getScheduler().runTaskAsynchronously(i, run);
		}
		
		@Override
		public void onConnect(SocketMessenger mess) {
			plugin().getLogger().info("Successfully connected to "+mess.getSocket().getInetAddress().getHostAddress());
			plugin().getServer().getPluginManager().callEvent(new ServerSocketConnectEvent(mess));
		}
	
		@Override
		public void onHandshake(SocketMessenger mess, String name) {
			plugin().getLogger().info("Successfully handshaked with "+mess.getSocket().getInetAddress().getHostAddress());
			plugin().getServer().getPluginManager().callEvent(new ServerSocketHandshakeEvent(mess, name));
		}
	
		@Override
		public void onMessage(SocketMessenger mess, JSONMap map) {
			plugin().getServer().getPluginManager().callEvent(new ServerSocketJSONEvent(mess, map));
		}
	
		@Override
		public void onDisconnect(SocketMessenger mess) {
			plugin().getServer().getPluginManager().callEvent(new ServerSocketDisconnectEvent(mess));
		}
	
		@Override
		public void log(String err) {
			if(plugin().config.getBoolean("debug", false)) plugin().getServer().getLogger().info(err);
		}
		
		public Socket4Bukkit plugin() {
			return Socket4Bukkit.get();
		}
	}
	
	public static class Client extends SocketClientApp {
		
		public static class ClientSocketConnectEvent extends Event {
			private SocketClient client;
			
			public ClientSocketConnectEvent(SocketClient client){
				this.client = client;
			}
			
			public SocketClient getClient(){
				return client;
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ClientSocketDisconnectEvent extends Event {
			
			private SocketClient client;

			public ClientSocketDisconnectEvent(SocketClient client){
				this.client = client;
			}
			
			public SocketClient getClient(){
				return client;
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ClientSocketJSONEvent extends Event {
			private JSONMap map;
			private SocketClient client;
			
			public ClientSocketJSONEvent(SocketClient client, JSONMap map){
				this.client = client;
				this.map = map;
			}
			
			public String getChannel(){
				return getExtraString("channel");
			}
			
			@SuppressWarnings("unchecked")
			public <T> T getExtra(String key, Class<T> type) {
				return (T) map.get(key);
			}
			
			public String getExtraString(String key) {
				return getExtra(key, String.class);
			}
			
			public int getExtraInt(String key) {
				return getExtra(key, int.class);
			}
			
			@SuppressWarnings("unchecked")
			public Map<String, Object> getExtraMap(String key) {
				return getExtra(key, Map.class);
			}
			
			@Deprecated
			public JSONMap getMap(){
				return map;
			}
			
			public SocketClient getClient(){
				return client;
			}
			
			public void write(Object... entries) {
				client.write(getChannel(), new JSONMap(entries));
			}
			
			public void write(JSONMap data) {
				client.write(getChannel(), data);
			}
			
			public void write(String data){
				client.write(getChannel(), data);
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		public static class ClientSocketHandshakeEvent extends Event {
			private SocketClient client;

			public ClientSocketHandshakeEvent(SocketClient client){
				this.client = client;
			}
			
			public SocketClient getClient() {
				return client;
			}
			
			public void write(String channel, JSONMap data) {
				client.write(channel, data);
			}
			
			public void write(String channel, String data) {
				client.write(channel, data);
			}
			
			private final static HandlerList handlers = new HandlerList();
			
			@Override
			public HandlerList getHandlers() {
				return handlers;
			}
			
			public static HandlerList getHandlerList(){
				return handlers;
			}
		}
		
		@Override
		public void run(Runnable run) {
			plugin().getServer().getScheduler().runTaskAsynchronously(i, run);
		}
		
		@Override
		public void onConnect(SocketClient client) {
			plugin().getLogger().info("Successfully connected to "+client.getSocket().getInetAddress().getHostAddress()+" on port "+client.getSocket().getPort());
			plugin().getServer().getPluginManager().callEvent(new ClientSocketConnectEvent(client));
		}
	
		@Override
		public void onHandshake(SocketClient client) {
			plugin().getLogger().info("Successfully handshaked with "+client.getSocket().getInetAddress().getHostAddress()+" on port "+client.getSocket().getPort());
			plugin().getServer().getPluginManager().callEvent(new ClientSocketHandshakeEvent(client));
		}
	
		@Override
		public void onMessage(SocketClient client, JSONMap map) {
			plugin().getServer().getPluginManager().callEvent(new ClientSocketJSONEvent(client, map));
		}
	
		@Override
		public void onDisconnect(SocketClient client) {
			plugin().getServer().getPluginManager().callEvent(new ClientSocketDisconnectEvent(client));
		}
	
		@Override
		public void log(String err) {
			if(plugin().config.getBoolean("debug", false)) plugin().getServer().getLogger().info(err);
		}
		
		public Socket4Bukkit plugin() {
			return Socket4Bukkit.get();
		}
	}
}