package net.lielamar.spleef.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.lielamar.spleef.listeners.custom.PlayerLoseEvent;
import net.lielamar.spleef.listeners.custom.SpectatorLeaveEvent;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.Game;
import net.lielamar.spleef.moduels.LoseReason;

public class PlayerLoseEvents implements Listener {

	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Game game = GameManager.getInstance().getPlayerGame(p);
		if(game == null) return;
		
		if(p.getLocation().getY() < game.getMap().getminY() && game.startedGame()) {
			PlayerLoseEvent event = new PlayerLoseEvent(p, game, LoseReason.FALL);
	        Bukkit.getPluginManager().callEvent(event);
	        if(event.isCancelled())
	        	return;
			game.getGs().removePlayer(p, LoseReason.FALL);
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		Game game = GameManager.getInstance().getPlayerGame(p);
		if(game != null) {
			PlayerLoseEvent event = new PlayerLoseEvent(p, game, LoseReason.QUIT_GAME);
	        Bukkit.getPluginManager().callEvent(event);
	        if(event.isCancelled())
	        	return;
	        
			game.getGs().removePlayer(p, LoseReason.QUIT_GAME);
		}
		
		game = GameManager.getInstance().getPlayerGameSpectator(p);
		if(game != null) {
			SpectatorLeaveEvent event = new SpectatorLeaveEvent(p, game);
	        Bukkit.getPluginManager().callEvent(event);
			game.getGs().removeSpectator(p);
		}
	}
}
