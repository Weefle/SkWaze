package fr.weefle.waze.skwrapper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.weefle.waze.Waze;

public class GetFromBungeeCord implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player){ // p.s. its only possible if its playerbinded not server!
            Player p = (Player) sender;
            if(args.length == 1){
               String s = (String) Waze.pcl.get(p, args[0].equalsIgnoreCase("nick"));
               p.sendMessage(ChatColor.BLUE + "Got: " + "\n" + ChatColor.GREEN + s);
            }
        }
        return true;
	}

}
