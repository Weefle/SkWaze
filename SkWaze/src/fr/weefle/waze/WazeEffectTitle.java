package fr.weefle.waze;

import javax.annotation.Nullable;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;

public class WazeEffectTitle extends Effect {
	
	private Expression<String> title;
	private Expression<String> subtitle;
	private Expression<Player> player;
	private Expression<Integer> time;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		// TODO Auto-generated method stub
		title = (Expression<String>) arg0[0];
		subtitle = (Expression<String>) arg0[1];
		player = (Expression<Player>) arg0[2];
		time = (Expression<Integer>) arg0[3];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return "send title to player";
	}

	@Override
	protected void execute(Event arg0) {
		// TODO Auto-generated method stub
	    IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + title.getSingle(arg0) + "\"}");
	    PacketPlayOutTitle endtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, basetitle);
	    
	    IChatBaseComponent basesubtitle = ChatSerializer.a("{\"text\": \"" + subtitle.getSingle(arg0) + "\"}");
	    PacketPlayOutTitle endsubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, basesubtitle);
	    
	    PacketPlayOutTitle endtime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, time.getSingle(arg0), 20);
	    
	    ((CraftPlayer)player.getSingle(arg0)).getHandle().playerConnection.sendPacket(endtitle);
	    ((CraftPlayer)player.getSingle(arg0)).getHandle().playerConnection.sendPacket(endsubtitle);
	    ((CraftPlayer)player.getSingle(arg0)).getHandle().playerConnection.sendPacket(endtime);
	}

}
