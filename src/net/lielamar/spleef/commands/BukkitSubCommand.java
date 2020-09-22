package net.lielamar.spleef.commands;

import org.bukkit.command.CommandSender;

public abstract class BukkitSubCommand {
	
	public BukkitSubCommand() {}
	
	public abstract void onCommand(CommandSender cs, String[] args);
	
	public abstract String getName();
	public abstract String getInfo();
	
	public abstract String[] aliases();
	
}
