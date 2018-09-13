package fr.weefle.waze.skwrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import fr.weefle.waze.Waze;

public class PluginChannelListener implements PluginMessageListener {

    private static HashMap<Player, Object> obj = new HashMap<Player, Object>();

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
    	
    	DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            String subchannel = in.readUTF();
            if(subchannel.equals("get")){
                String input = in.readUTF();
                obj.put(player, input);
         
                notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }

    public synchronized Object get(Player p, boolean integer) {  // here you can add parameters (e.g. String table, String column, ...)
    	sendToBungeeCord(p, "get", integer ? "points" : "nickname");
    	 
        try {
            wait();
        } catch(InterruptedException e){}
 
        return obj.get(p);
    }

    public void sendToBungeeCord(Player p, String channel, String sub){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(channel);
            out.writeUTF(sub);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendPluginMessage(Waze.getPlugin(Waze.class), "BungeeCord", b.toByteArray());
    }

}
