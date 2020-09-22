package net.lielamar.spleef.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.Game;

public class OnCommandMidGame implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Game game = GameManager.getInstance().getPlayerGame(e.getPlayer());
		if(game == null) {
			game = GameManager.getInstance().getPlayerGameSpectator(e.getPlayer());
			
			if(game == null) return;
		}
		
		if(!e.getMessage().equalsIgnoreCase("/spleef leave")
				&& !e.getMessage().equalsIgnoreCase("/spleef quit")
				&& !e.getMessage().equalsIgnoreCase("/spleef quitqueue")
				&& !e.getMessage().equalsIgnoreCase("/spleef leavequeue")
				&& !e.getMessage().equalsIgnoreCase("/spleef force")
				&& !e.getMessage().equalsIgnoreCase("/spleef start")
				&& !e.getMessage().equalsIgnoreCase("/spleef forcestart")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You can't use commands while in a game. Please do /spleef leave before doing a command!");
		}
	}
	
}
