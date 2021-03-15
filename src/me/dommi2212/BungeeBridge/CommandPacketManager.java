package me.dommi2212.BungeeBridge;

import java.util.List;

import fr.weefle.waze.Waze;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * The CommandExecutor used to manage the command "PacketManager"
 * Aliases: PacketMan, PacketMon, PacketMonitor
 */
public class CommandPacketManager implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!cs.hasPermission("bungeebridge.packetmanager")) {
			cs.sendMessage("You don't have the permission to use this command!");
			return false;
		}
		if(!Waze.isLoggerEnabled()) {
			cs.sendMessage("The packetlogger/packetmanager is disabled! Change this setting in your config.yml and try again!");
			return false;
		}
		if(args.length == 0) {
			cs.sendMessage("Syntax: /PacketMan List|Notify [args...]");
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				cs.sendMessage("Packets sent since last restart/reload:");
				for(BungeePacketType type : BungeePacketType.values()) {
					TypeCountEntry entry = TypeCountEntry.getByType(type);
					if(entry.getCount() > 0) {
						cs.sendMessage(type + " (" + entry.getCount() + ")");
					}
				}
			} else if(args[0].equalsIgnoreCase("notify")) cs.sendMessage("Syntax: /PacketMan Notify <IDs>");
			else cs.sendMessage("Syntax: /PacketMan List|Notify [args...]");
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("notify")) {			
				try {
					List<BungeePacketType> types = PacketSubscriptionManager.getPacketsByString(args[1]);
					if(types.isEmpty()) cs.sendMessage("Please provide atleast one id!");
					PacketSubscriptionManager.setSubscriptions(cs, types);
				} catch (InvalidFormatException e) {
					if(e.getMessage().equalsIgnoreCase("Invalid Format!")) {
						cs.sendMessage("Format: ID OR ID,ID,ID,... OR ID-ID OR ID,ID,ID-ID,ID-ID,...");
					} else if(e.getMessage().equalsIgnoreCase("Unknown packet!")) {
						cs.sendMessage("There is no packet by this id!");
					} else if(e.getMessage().equalsIgnoreCase("Invalid character(s)!")) {
						cs.sendMessage("Only numeric characters allowed!");
					} else throw new IllegalArgumentException();
				}
			} else if(args[0].equalsIgnoreCase("list")) cs.sendMessage("Syntax: /PacketMan List");
			else cs.sendMessage("Syntax: /PacketMan List|Notify [args...]");
		} else cs.sendMessage("Syntax: /PacketMan List|Notify [args...]");
		
		return true;
	}


}
