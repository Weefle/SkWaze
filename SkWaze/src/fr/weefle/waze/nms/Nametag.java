package fr.weefle.waze.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Nametag {
	
	//make this part reflective
	
	/*public void changeName(Player p, String newName){
        for(Player pl : Bukkit.getServer().getOnlinePlayers()){
            if(pl == p) continue;
            //REMOVES THE PLAYER
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)p).getHandle()));
            //CHANGES THE PLAYER'S GAME PROFILE
            GameProfile gp = ((CraftPlayer)p).getProfile();
            try {
                Field nameField = GameProfile.class.getDeclaredField("name");
                nameField.setAccessible(true);

                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);

                nameField.set(gp, newName);
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                throw new IllegalStateException(ex);
            }
            //ADDS THE PLAYER
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)p).getHandle()));
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(p.getEntityId()));
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle()));
        }
    }*/
	@SuppressWarnings("deprecation")
	public void changeName(String name, Player player) {
	     try {
	       Method getHandle = player.getClass().getMethod("getHandle",
	           (Class<?>[]) null);
	       // Object entityPlayer = getHandle.invoke(player);
	       // Class<?> entityHuman = entityPlayer.getClass().getSuperclass();
	       /**
	        * These methods are no longer needed, as we can just access the
	        * profile using handle.getProfile. Also, because we can just use
	        * the method, which will not change, we don't have to do any
	        * field-name look-ups.
	        */
	       try {
	         Class.forName("com.mojang.authlib.GameProfile");
	         // By having the line above, only 1.8+ servers will run this.
	       } catch (ClassNotFoundException e) {
	         /**
	          * Currently, there is no field that can be easily modified for
	          * lower versions. The "name" field is final, and cannot be
	          * modified in runtime. The only workaround for this that I can
	          * think of would be if the server creates a "dummy" entity that
	          * takes in the player's input and plays the player's animations
	          * (which will be a lot more lines)
	          */
	         Bukkit.broadcastMessage("CHANGE NAME METHOD DOES NOT WORK IN 1.7 OR LOWER!");
	         return;
	       }
	         Object profile = getHandle.invoke(player).getClass()
	             .getMethod("getProfile")
	             .invoke(getHandle.invoke(player));
	         Field ff = profile.getClass().getDeclaredField("name");
	         ff.setAccessible(true);
	         ff.set(profile, name);
	       for (Player players : Bukkit.getOnlinePlayers()) {
	         players.hidePlayer(player);
	         players.showPlayer(player);
	       }
	     } catch (NoSuchMethodException | SecurityException
	         | IllegalAccessException | IllegalArgumentException
	         | InvocationTargetException | NoSuchFieldException e) {
	       /**
	        * Merged all the exceptions. Less lines
	        */
	       e.printStackTrace();
	     }
	   }

}
