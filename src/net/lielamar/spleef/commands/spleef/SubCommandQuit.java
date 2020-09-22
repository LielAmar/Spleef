package net.lielamar.spleef.commands.spleef;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.lielamar.spleef.commands.BukkitSubCommand;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.utils.Messages;

public class SubCommandQuit extends BukkitSubCommand {

	@Override
	public void onCommand(CommandSender cs, String[] args) {
		Player p = (Player)cs;
		
		if(!p.hasPermission("spleef.command.quit")) {
			p.sendMessage(Messages.noPermissions);
			return;
		}
		p.sendMessage(GameManager.getInstance().quitPlayer(p));
		return;
	}

	@Override
	public String getName() {
		return "quit";
	}

	@Override
	public String getInfo() {
		return ChatColor.RED + "Usage: /Spleef <Quit>";
	}

	@Override
	public String[] aliases() {
		return new String[] {"leave", "quitqueue", "leavequeue"};
	}

}
