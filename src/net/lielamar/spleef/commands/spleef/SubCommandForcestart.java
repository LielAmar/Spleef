package net.lielamar.spleef.commands.spleef;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.lielamar.spleef.commands.BukkitSubCommand;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.utils.Messages;

public class SubCommandForcestart extends BukkitSubCommand {

	@Override
	public void onCommand(CommandSender cs, String[] args) {
		Player p = (Player)cs;
		
		if(!p.hasPermission("spleef.command.forcestart")) {
			p.sendMessage(Messages.noPermissions);
			return;
		}

		p.sendMessage(GameManager.getInstance().forcestartPlayer(p));
		return;
	}
	
	@Override
	public String getName() {
		return "forcestart";
	}

	@Override
	public String getInfo() {
		return ChatColor.RED + "Usage: /Spleef <Forcestart>";
	}

	@Override
	public String[] aliases() {
		return new String[] {"forcestart", "start", "force"};
	}
}
