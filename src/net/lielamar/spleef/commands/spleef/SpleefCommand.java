package net.lielamar.spleef.commands.spleef;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.lielamar.spleef.commands.BukkitCommand;
import net.lielamar.spleef.commands.BukkitSubCommand;
import net.lielamar.spleef.utils.Messages;

public class SpleefCommand implements CommandExecutor, BukkitCommand {

	private static SpleefCommand instance = new SpleefCommand();
	private SpleefCommand() {}
	public static SpleefCommand getInstance() {
		return instance;
	}
	
	private List<BukkitSubCommand> subcommands = new ArrayList<BukkitSubCommand>();

	public final String spleef = "spleef";
	
    /**
     * Sets the Spleef sub-commands
     */
	public void setup(JavaPlugin plugin) {
		plugin.getCommand(spleef).setExecutor(this);
		this.subcommands.add(new SubCommandJoin());
		this.subcommands.add(new SubCommandQuit());
		this.subcommands.add(new SubCommandSpectate());
		this.subcommands.add(new SubCommandForcestart());
		this.subcommands.add(new SubCommandMap());
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase(spleef)) {
			if(!(cs instanceof Player)) {
				cs.sendMessage(Messages.mustBeAPlayer);
				return true;
			}
			
			Player p = (Player)cs;
			
			if(args.length == 0) {
				if(!p.hasPermission("spleef.commandlist")) {
					p.sendMessage(Messages.noPermissions);
					return true;
				}
				commandList(p);
				return true;
			}
			
			BukkitSubCommand subcommand = getSubCommand(args[0].toLowerCase());
			if(subcommand == null) {
				if(!p.hasPermission("spleef.commandlist")) {
					p.sendMessage(Messages.noPermissions);
					return true;
				}
				commandList(p);
				return true;
			}
			
			String[] arguments = new String[args.length-1];
			for(int i = 0; i < arguments.length; i++)
				arguments[i] = args[i+1];
			
			try {
				subcommand.onCommand(cs, arguments);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}
	
	/**
     * @param name      Name of a Sub-Command
     * @return          A {@link com.lielamar.armsrace.commands.subcommands.SubCommand} instance
     */
	public BukkitSubCommand getSubCommand(String name) {
		Iterator<BukkitSubCommand> subcommands = this.subcommands.iterator();
		while(subcommands.hasNext()) {
			BukkitSubCommand sc = (BukkitSubCommand) subcommands.next();
			if(sc.getName().equalsIgnoreCase(name))
				return sc;
			
			for(String s : sc.aliases())
				if(s.equalsIgnoreCase(name))
					return sc;
		}
		return null;
	}
	
	/**
	 * @param p     The player to print the command list to
	 */
	public void commandList(Player p) {
		p.sendMessage(ChatColor.YELLOW + "===== SPLEEF =====");
		if(p.hasPermission("spleef.command.join")) p.sendMessage(ChatColor.AQUA + "/spleef join");
		if(p.hasPermission("spleef.command.quit")) p.sendMessage(ChatColor.AQUA + "/spleef quit");
		if(p.hasPermission("spleef.command.forcestart")) p.sendMessage(ChatColor.AQUA + "/spleef forcestart");
		if(p.hasPermission("spleef.command.spectate")) p.sendMessage(ChatColor.AQUA + "/spleef spectate <player>");
		if(p.hasPermission("spleef.command.map")) p.sendMessage(ChatColor.AQUA + "/spleef map");
		if(p.hasPermission("spleef.command.map.list")) p.sendMessage(ChatColor.AQUA + "/spleef map list");
		if(p.hasPermission("spleef.command.map.create")) p.sendMessage(ChatColor.AQUA + "/spleef map create");
		if(p.hasPermission("spleef.command.map.remove")) p.sendMessage(ChatColor.AQUA + "/spleef map remove");
		if(p.hasPermission("spleef.command.map.setminy")) p.sendMessage(ChatColor.AQUA + "/spleef map setminy");
		if(p.hasPermission("spleef.command.map.setspawn")) p.sendMessage(ChatColor.AQUA + "/spleef map setspawn");
		if(p.hasPermission("spleef.command.map.setlocation")) p.sendMessage(ChatColor.AQUA + "/spleef map setlocation");
	}
}