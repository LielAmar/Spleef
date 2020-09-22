package net.lielamar.spleef.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.Game;

public class StatusEvents implements Listener {

	@EventHandler
	public void hunger(FoodLevelChangeEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player)e.getEntity();
		Game game = GameManager.getInstance().getPlayerGame(p);
		if(game == null) {
			game = GameManager.getInstance().getPlayerGameSpectator(p);
			
			if(game == null) return;
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void health(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		
		Player p = (Player)e.getEntity();
		Game game = GameManager.getInstance().getPlayerGame(p);
		if(game == null) {
			game = GameManager.getInstance().getPlayerGameSpectator(p);
			
			if(game == null) return;
		}
		
		e.setCancelled(true);
	}
}
